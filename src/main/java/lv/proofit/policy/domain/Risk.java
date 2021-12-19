package lv.proofit.policy.domain;


import lv.proofit.policy.domain.enumeration.ThresholdComparison;

import java.io.Serializable;

/**
 * A Risk
 */
public class Risk implements Serializable {

    private String riskType;

    private Double defaultCoefficient;

    private Double thresholdAmount;

    private Double thresholdCoefficient;

    private ThresholdComparison thresholdComparison;

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    /**
     * @return the default risk coefficient
     */
    public Double getDefaultCoefficient() {
        return defaultCoefficient;
    }

    public void setDefaultCoefficient(Double defaultCoefficient) {
        this.defaultCoefficient = defaultCoefficient;
    }

    /**
     * insured sum amount level,
     * used to select between the risk coefficient and threshold coefficient
     *
     * @return insured sum amount level
     */
    public Double getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(Double thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    /**
     * threshold coefficient
     *
     * @return threshold coefficient
     */
    public Double getThresholdCoefficient() {
        return thresholdCoefficient;
    }

    public void setThresholdCoefficient(Double thresholdCoefficient) {
        this.thresholdCoefficient = thresholdCoefficient;
    }

    /**
     * tells how to compare the sum insured and the threshold amount
     *
     * @return ThresholdComparison
     */
    public ThresholdComparison getThresholdComparison() {
        return thresholdComparison;
    }

    public void setThresholdComparison(ThresholdComparison thresholdComparison) {
        this.thresholdComparison = thresholdComparison;
    }
}
