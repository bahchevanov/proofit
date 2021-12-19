package lv.proofit.policy.domain;


import lv.proofit.policy.domain.enumeration.RiskType;
import lv.proofit.policy.domain.enumeration.ThresholdComparison;

import java.io.Serializable;

/**
 * A Risk
 */
public class Risk implements Serializable {

    private RiskType riskType;
    private Double defaultCoefficient;
    private Double thresholdAmount;
    private Double thresholdCoefficient;
    private ThresholdComparison thresholdComparison;

    public RiskType getRiskType() {
        return riskType;
    }

    public void setRiskType(RiskType riskType) {
        this.riskType = riskType;
    }

    public Double getDefaultCoefficient() {
        return defaultCoefficient;
    }

    public void setDefaultCoefficient(Double defaultCoefficient) {
        this.defaultCoefficient = defaultCoefficient;
    }

    public Double getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(Double thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    public Double getThresholdCoefficient() {
        return thresholdCoefficient;
    }

    public void setThresholdCoefficient(Double thresholdCoefficient) {
        this.thresholdCoefficient = thresholdCoefficient;
    }

    public ThresholdComparison getThresholdComparison() {
        return thresholdComparison;
    }

    public void setThresholdComparison(ThresholdComparison thresholdComparison) {
        this.thresholdComparison = thresholdComparison;
    }
}
