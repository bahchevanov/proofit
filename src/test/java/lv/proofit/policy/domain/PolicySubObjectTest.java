package lv.proofit.policy.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PolicySubObjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicySubObject.class);
        PolicySubObject PolicySubObject1 = new PolicySubObject();
        PolicySubObject1.setName("name1");
        PolicySubObject1.setRiskType("FIRE");
        PolicySubObject PolicySubObject2 = new PolicySubObject();
        PolicySubObject2.setName(PolicySubObject1.getName());
        PolicySubObject2.setRiskType(PolicySubObject1.getRiskType());
        assertThat(PolicySubObject1).isEqualTo(PolicySubObject2);
        PolicySubObject2.setName("name2");
        assertThat(PolicySubObject1).isNotEqualTo(PolicySubObject2);
        PolicySubObject1.setName(null);
        assertThat(PolicySubObject1).isNotEqualTo(PolicySubObject2);
    }
}
