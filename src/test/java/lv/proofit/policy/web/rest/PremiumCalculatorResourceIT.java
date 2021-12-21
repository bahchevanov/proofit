package lv.proofit.policy.web.rest;

import lv.proofit.policy.ProofItApp;
import lv.proofit.policy.domain.Policy;
import lv.proofit.policy.domain.PolicyObject;
import lv.proofit.policy.domain.PolicySubObject;
import lv.proofit.policy.domain.TestUtil;
import lv.proofit.policy.domain.enumeration.PolicyStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link PremiumCalculatorResource} REST controller.
 */
@SpringBootTest(classes = ProofItApp.class)
@AutoConfigureMockMvc
class PremiumCalculatorResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";

    private static final Double DEFAULT_INSURANCE_SUM = 1D;
    private static final Double UPDATED_INSURANCE_SUM = 2D;

    private static final String RISK_TYPE_FIRE = "FIRE";
    private static final String RISK_TYPE_THEFT = "THEFT";

    private static final PolicyStatus DEFAULT_STATUS = PolicyStatus.REGISTERED;

    private static final String ENTITY_API_URL = "/api/calculate";

    @Autowired
    private MockMvc restPolicyMockMvc;

    private Policy policy1;
    private Policy policy2;
    private Policy policy3;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Policy createEntity(double sum1, double sum2) {
        Policy policy = new Policy().withNumber(DEFAULT_NUMBER).withStatus(DEFAULT_STATUS);
        PolicyObject policyObject = new PolicyObject().withName(DEFAULT_NAME);

        Set<PolicySubObject> policySubObjects = Set.of(new PolicySubObject()
                .withName(DEFAULT_NAME)
                .withInsuranceSum(sum1)
                .withRiskType(RISK_TYPE_FIRE),
            new PolicySubObject()
                .withName(DEFAULT_NAME)
                .withInsuranceSum(sum2)
                .withRiskType(RISK_TYPE_THEFT));

        policyObject.withPolicySubObjects(policySubObjects);
        policy.withPolicyObjects(Set.of(policyObject));

        return policy;
    }




    @BeforeEach
    public void initTest() {
        policy1 = createEntity(100, 8);
        policy2 = createEntity(500, 102.51);
    }

    @Test
    void calculatePremium() throws Exception {
        String result1 = restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy1)))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(result1).isEqualTo("2.28");


        String result2 = restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy2)))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(result2).isEqualTo("17.13");
    }

    @Test
    void testPolicyIsNull() throws Exception {
        policy1=null;
        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy1)))
            .andExpect(status().isBadRequest());


    }

    @Test
    void testPolicyNumberIsRequired() throws Exception {
        policy1.setNumber(null);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy1)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testPolicyStatusIsRequired() throws Exception {
        policy1.setStatus(null);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy1)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testPolicyObjectsRequired() throws Exception {
        policy1.setPolicyObjects(null);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy1)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testPolicyObjectNameIsRequired() throws Exception {
        policy1.getPolicyObjects().stream().findAny().get().setName(null);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy1)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testPolicyObjectSubObjectsRequired() throws Exception {
        policy1.getPolicyObjects().stream().findAny().get().setPolicySubObjects(null);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy1)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testAnySumInsuredMustBeBiggerThanZero() throws Exception {
        policy1 = createEntity(1,-1);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy1)))
            .andExpect(status().isBadRequest());

        policy1 = createEntity(-1,1);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy1)))
            .andExpect(status().isBadRequest());

        policy1 = createEntity(1,-0);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy1)))
            .andExpect(status().isBadRequest());

        policy1 = createEntity(-0,1);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(policy1)))
            .andExpect(status().isBadRequest());
    }

}
