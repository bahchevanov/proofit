package lv.proofit.policy.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PolicyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Policy.class);
        Policy policy1 = new Policy();
        policy1.setNumber("num1");
        Policy policy2 = new Policy();
        policy2.setNumber(policy1.getNumber());
        assertThat(policy1).isEqualTo(policy2);
        policy2.setNumber("num2");
        assertThat(policy1).isNotEqualTo(policy2);
        policy1.setNumber(null);
        assertThat(policy1).isNotEqualTo(policy2);
    }
}
