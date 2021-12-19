package lv.proofit.policy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @JsonIgnoreProperties(value = {"policySubObjects", "policy"}, allowSetters = true)
    private PolicyObject policyObject;

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

    public PolicyObject getPolicyObject() {
        return policyObject;
    }

    public void setPolicyObject(PolicyObject policyObject) {
        this.policyObject = policyObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolicySubObject that = (PolicySubObject) o;
        return name.equals(that.name) && riskType.equals(that.riskType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, riskType);
    }
}
