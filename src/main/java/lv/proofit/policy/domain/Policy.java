package lv.proofit.policy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lv.proofit.policy.domain.enumeration.PolicyStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Policy.
 */
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String number;

    @NotNull
    private PolicyStatus status;

    @JsonIgnoreProperties(value = {"policySubObjects", "policy"}, allowSetters = true)
    private Set<PolicyObject> policyObjects = new HashSet<>();

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }

    public Set<PolicyObject> getPolicyObjects() {
        return policyObjects;
    }

    public void setPolicyObjects(Set<PolicyObject> policyObjects) {
        this.policyObjects = policyObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Policy policy = (Policy) o;
        return number !=null && Objects.equals(number, policy.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
