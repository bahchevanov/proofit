package lv.proofit.policy.service;

import lv.proofit.policy.config.ApplicationProperties;
import lv.proofit.policy.config.RiskProperties;
import lv.proofit.policy.domain.Policy;
import lv.proofit.policy.domain.PolicyObject;
import lv.proofit.policy.domain.PolicySubObject;
import lv.proofit.policy.domain.Risk;
import lv.proofit.policy.domain.enumeration.ThresholdComparison;
import lv.proofit.policy.service.error.PolicyNotValidException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A PremiumCalculator.
 * Calculates premium of a policy
 */
@Service
@Validated
public class PremiumCalculator {

    private final RiskProperties riskProperties;

    public PremiumCalculator(ApplicationProperties properties) {
        this.riskProperties = properties.getRiskProperties();
    }

    /**
     * calculates policy premium
     *
     * @param policy The policy to calculate its premium
     * @return the calculated premium
     */
    public Double calculate(@Valid @NotNull Policy policy) throws PolicyNotValidException {

        //  groups per risk type and total sum insured of sub-objects
        Map<String, Double> sumPerRiskType = policy.getPolicyObjects()
            .stream()
            .map(PolicyObject::getPolicySubObjects)
            .flatMap(Set::stream)
            .collect(Collectors.groupingBy(PolicySubObject::getRiskType,
                Collectors.summingDouble(PolicySubObject::getInsuranceSum)));

        //sums the sub-premiums for every type
        Double premium = calculate(sumPerRiskType);

        return premium;
    }

    protected Double calculate(@NotEmpty Map<String, Double> sumPerRiskType) throws PolicyNotValidException {
        //sums the sub-premiums for every type

        Double premium = 0.;
        for (Map.Entry<String, Double> entry : sumPerRiskType.entrySet()) {
            double subPremium = calculateByRisk(entry.getKey(), entry.getValue());
            premium = Double.sum(premium, subPremium);
        }

        return premium;
    }

    /**
     * calculates the sub-premium by the risk type,
     * based on the risk specific business logic
     *
     * @param riskType   type of the Risk
     * @param sumInsured sub-object insurance sum
     * @return the calculated sub-premium
     */
    protected double calculateByRisk(String riskType, Double sumInsured) throws PolicyNotValidException {

        if (sumInsured == null || Double.compare(sumInsured, 0.0) <= 0) {
            throw new PolicyNotValidException("SubObjects total sum insured is negative or zero for risk type: " + riskType);
        }
        Risk risk = resolveRisk(riskType);
        // gets the applied coefficient
        double coefficient = resolveCoefficient(sumInsured, risk);
        BigDecimal subPremium = new BigDecimal(sumInsured).multiply(new BigDecimal(coefficient));

        // this will set the digits after the comma
        int scale = riskProperties.getPremiumScale();

        return subPremium.setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * resolves the risk based on the type
     * will return the default one iif the type is not found
     *
     * @param riskType type of the risk
     * @return the resolved risk or default one
     */
    public Risk resolveRisk(String riskType) {
        return riskProperties.getRiskByTypeOrDefault(riskType);
    }

    /**
     * elects the risk coefficient based on the business logic,
     * selects between default coefficient and the threshold coefficient
     * if the insured sum exceeds certain level
     *
     * @param sumInsured insured sum
     * @param risk       the risk
     * @return selected coefficient
     */
    public double resolveCoefficient(Double sumInsured, Risk risk) {
        int compare = Double.compare(sumInsured, risk.getThresholdAmount());

        double coefficient = risk.getDefaultCoefficient();
        if (compare == 0 && risk.getThresholdComparison() == ThresholdComparison.EGT) {
            coefficient = risk.getThresholdCoefficient();
        } else if (compare > 0) {
            coefficient = risk.getThresholdCoefficient();
        }

        return coefficient;
    }
}
