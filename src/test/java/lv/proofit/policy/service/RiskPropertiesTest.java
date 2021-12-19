package lv.proofit.policy.service;

import lv.proofit.policy.ProofItApp;
import lv.proofit.policy.config.ApplicationProperties;
import lv.proofit.policy.config.RiskProperties;
import lv.proofit.policy.domain.Risk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ProofItApp.class)
public class RiskPropertiesTest {

    @Autowired
    private ApplicationProperties properties;

    private RiskProperties riskProperties;

    @BeforeEach
    private void setup() {
        riskProperties = properties.getRiskProperties();
    }

    @Test
    public void testDefaultRiskIsInitialized() {
        assertThat(riskProperties).isNotNull();
        assertThat(riskProperties.getDefaultRisk()).isNotNull();
        assertThat(riskProperties.getDefaultRisk().getDefaultCoefficient()).isNotNull();
        assertThat(riskProperties.getDefaultRisk().getThresholdCoefficient()).isNotNull();
        assertThat(riskProperties.getDefaultRisk().getRiskType()).isNotNull();
        assertThat(riskProperties.getDefaultRisk().getRiskType()).isNotEqualTo("");
        assertThat(riskProperties.getDefaultRisk().getThresholdAmount()).isNotNull();
        assertThat(riskProperties.getDefaultRisk().getThresholdComparison()).isNotNull();
    }

    @Test
    public void testRisksMapIsInitialized() {
        assertThat(riskProperties).isNotNull();
        assertThat(riskProperties.getRisks()).isNotNull();
        assertThat(riskProperties.getRisks()).isNotEmpty();

        Collection<Risk> risks = riskProperties.getRisks().values();
        for (Risk risk : risks) {
            assertThat(risk.getDefaultCoefficient()).isNotNull();
            assertThat(risk.getThresholdCoefficient()).isNotNull();
            assertThat(risk.getRiskType()).isNotNull();
            assertThat(risk.getRiskType()).isNotEqualTo("");
            assertThat(risk.getThresholdAmount()).isNotNull();
            assertThat(risk.getThresholdComparison()).isNotNull();
        }
    }

    @Test
    public void testPremiumScaleIsInitialized() {
        assertThat(riskProperties.getPremiumScale()).isNotNull();
    }
}
