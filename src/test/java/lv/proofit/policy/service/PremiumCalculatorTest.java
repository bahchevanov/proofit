package lv.proofit.policy.service;

import lv.proofit.policy.ProofItApp;
import lv.proofit.policy.config.ApplicationProperties;
import lv.proofit.policy.domain.Risk;
import lv.proofit.policy.domain.enumeration.ThresholdComparison;
import lv.proofit.policy.service.error.PolicyNotValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ProofItApp.class)
public class PremiumCalculatorTest {

    private final static String FIRE_TYPE = "FIRE";
    private final static String THEFT_TYPE = "THEFT";
    private final static String OTHER_TYPE = "OTHER";
    private final static String UNKNOWN_TYPE = "UNKNOWN";
    private Risk fireRisk;
    private Risk theftRisk;

    @Autowired
    private ApplicationProperties properties;

    private PremiumCalculator calculator;

    @BeforeEach
    private void setup() {
        calculator = new PremiumCalculator(properties);
        fireRisk = properties.getRiskProperties().getRisks().get(FIRE_TYPE);
        theftRisk = properties.getRiskProperties().getRisks().get(THEFT_TYPE);
    }

    @Test
    public void testResolveCoefficient() {
        testResolveCoefficient(fireRisk, ThresholdComparison.GT);
        testResolveCoefficient(theftRisk, ThresholdComparison.EGT);
    }

    private void testResolveCoefficient(Risk risk, ThresholdComparison thresholdComparison) {
        final double zero = 0.;
        final double minusOne = -1.;
        double belowThreshold = risk.getThresholdAmount() - 0.01;
        double aboveThreshold = risk.getThresholdAmount() + 0.01;

        double coefficient = calculator.resolveCoefficient(belowThreshold, risk);
        assertThat(coefficient).isEqualTo(risk.getDefaultCoefficient());

        coefficient = calculator.resolveCoefficient(zero, risk);
        assertThat(coefficient).isEqualTo(risk.getDefaultCoefficient());

        coefficient = calculator.resolveCoefficient(minusOne, risk);
        assertThat(coefficient).isEqualTo(risk.getDefaultCoefficient());

        assertThat(thresholdComparison).isEqualTo(risk.getThresholdComparison());

        coefficient = calculator.resolveCoefficient(aboveThreshold, risk);
        assertThat(coefficient).isEqualTo(risk.getThresholdCoefficient());
    }

    @Test
    public void testResolveRisk() {
        Risk risk1 = calculator.resolveRisk(FIRE_TYPE);
        assertThat(risk1).isNotNull();
        assertThat(risk1.getRiskType()).isEqualTo(FIRE_TYPE);

        Risk risk2 = calculator.resolveRisk(THEFT_TYPE);
        assertThat(risk2).isNotNull();
        assertThat(risk2.getRiskType()).isEqualTo(THEFT_TYPE);

        Risk risk3 = calculator.resolveRisk(UNKNOWN_TYPE);
        assertThat(risk3).isNotNull();
        assertThat(risk3.getRiskType()).isEqualTo(OTHER_TYPE);
    }

    @Test
    public void testCalculateByRisk() throws PolicyNotValidException {
        testCalculateFireRisk();
        testCalculateTheftRisk();
    }

    private void testCalculateFireRisk() throws PolicyNotValidException {


        double sunInsured = Double.MAX_VALUE;
        double premium = calculator.calculateByRisk(FIRE_TYPE, sunInsured);
        assertThat(premium).isNotEqualTo(0.);

        sunInsured = 100;
        premium = calculator.calculateByRisk(FIRE_TYPE, sunInsured);
        assertThat(premium).isNotNull();
        assertThat(premium).isEqualTo(1.4);

        sunInsured = 500;
        premium = calculator.calculateByRisk(FIRE_TYPE, sunInsured);
        assertThat(premium).isNotNull();
        assertThat(premium).isEqualTo(12);

        String err = "SubObjects total sum insured is negative or zero for risk type: " + FIRE_TYPE;
        PolicyNotValidException thrown = Assertions.assertThrows(PolicyNotValidException.class, () -> {
            calculator.calculateByRisk(FIRE_TYPE, 0.);
        }, err);
        Assertions.assertEquals(err, thrown.getMessage());

        err = "SubObjects total sum insured is negative or zero for risk type: " + FIRE_TYPE;
        thrown = Assertions.assertThrows(PolicyNotValidException.class, () -> {
            calculator.calculateByRisk(FIRE_TYPE, -100.);
        }, err);
        Assertions.assertEquals(err, thrown.getMessage());
    }

    private void testCalculateTheftRisk() throws PolicyNotValidException {

        double sunInsured = Double.MAX_VALUE;
        double premium = calculator.calculateByRisk(THEFT_TYPE, sunInsured);
        assertThat(premium).isNotEqualTo(0.);

        sunInsured = 8;
        premium = calculator.calculateByRisk(THEFT_TYPE, sunInsured);
        assertThat(premium).isNotNull();
        assertThat(premium).isEqualTo(0.88);

        sunInsured = 102.51;
        premium = calculator.calculateByRisk(THEFT_TYPE, sunInsured);
        assertThat(premium).isNotNull();
        assertThat(premium).isEqualTo(5.13);

        String err = "SubObjects total sum insured is negative or zero for risk type: " + THEFT_TYPE;
        PolicyNotValidException thrown = Assertions.assertThrows(PolicyNotValidException.class, () -> {
            calculator.calculateByRisk(THEFT_TYPE, 0.);
        }, err);
        Assertions.assertEquals(err, thrown.getMessage());

        err = "SubObjects total sum insured is negative or zero for risk type: " + THEFT_TYPE;
        thrown = Assertions.assertThrows(PolicyNotValidException.class, () -> {
            calculator.calculateByRisk(THEFT_TYPE, -100.);
        }, err);
        Assertions.assertEquals(err, thrown.getMessage());
    }
}
