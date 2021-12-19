package lv.proofit.policy.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PolicyObjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyObject.class);
        PolicyObject policyObject1 = new PolicyObject();
        policyObject1.setName("name1");
        PolicyObject policyObject2 = new PolicyObject();
        policyObject2.setName(policyObject1.getName());
        assertThat(policyObject1).isEqualTo(policyObject2);
        policyObject2.setName("name2");
        assertThat(policyObject1).isNotEqualTo(policyObject2);
        policyObject1.setName(null);
        assertThat(policyObject1).isNotEqualTo(policyObject2);
    }
}
