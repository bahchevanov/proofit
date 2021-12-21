package lv.proofit.policy.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PolicySubObject.
 */
public class PolicySubObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    @NotNull
    private Double insuranceSum;

    @NotNull
    private String riskType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getInsuranceSum() {
        return insuranceSum;
    }

    public void setInsuranceSum(Double insuranceSum) {
        this.insuranceSum = insuranceSum;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolicySubObject that = (PolicySubObject) o;
        return name != null && riskType != null
            && Objects.equals(name, that.name) && Objects.equals(riskType, that.riskType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, riskType);
    }

    public PolicySubObject withName(String name) {
        this.name = name;
        return this;
    }

    public PolicySubObject withInsuranceSum(Double insuranceSum) {
        this.insuranceSum = insuranceSum;
        return this;
    }

    public PolicySubObject withRiskType(String riskType) {
        this.riskType = riskType;
        return this;
    }
}
