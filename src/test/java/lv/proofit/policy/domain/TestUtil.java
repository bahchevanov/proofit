package lv.proofit.policy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lv.proofit.policy.domain.enumeration.PolicyStatus;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Utility class for testing objects
 */
public final class TestUtil {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String RISK_TYPE_FIRE = "FIRE";
    private static final String RISK_TYPE_THEFT = "THEFT";
    private static final PolicyStatus DEFAULT_STATUS = PolicyStatus.REGISTERED;

    private static final ObjectMapper mapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
public static String tojson(Policy policy) throws JsonProcessingException {
    String s = mapper.writeValueAsString(policy);
    return s;
}
    /**
     * Verifies the equals/hashcode contract on the domain object.
     */
    public static <T> void equalsVerifier(Class<T> clazz) throws Exception {
        T domainObject1 = clazz.getConstructor().newInstance();
        assertThat(domainObject1.toString()).isNotNull();
        assertThat(domainObject1).isEqualTo(domainObject1);
        assertThat(domainObject1).hasSameHashCodeAs(domainObject1);
        // Test with an instance of another class
        Object testOtherObject = new Object();
        assertThat(domainObject1).isNotEqualTo(testOtherObject);
        assertThat(domainObject1).isNotEqualTo(null);
        // Test with an instance of the same class
        T domainObject2 = clazz.getConstructor().newInstance();
        assertThat(domainObject1).isNotEqualTo(domainObject2);
        // HashCodes are equals because the objects are not persisted yet
        assertThat(domainObject1).hasSameHashCodeAs(domainObject2);
    }

    /**
     * Convert an object to JSON byte array.
     *
     * @param object the object to convert.
     * @return the JSON byte array.
     * @throws IOException
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }

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
}
