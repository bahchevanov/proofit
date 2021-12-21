package lv.proofit.policy.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A PolicyObject.
 */
public class PolicyObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    @Valid
    @NotEmpty
    private Set<PolicySubObject> policySubObjects = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PolicySubObject> getPolicySubObjects() {
        return policySubObjects;
    }

    public void setPolicySubObjects(Set<PolicySubObject> policySubObjects) {
        this.policySubObjects = policySubObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolicyObject that = (PolicyObject) o;
        return name != null && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public PolicyObject withName(String name) {
        this.name = name;
        return this;
    }

    public PolicyObject withPolicySubObjects(Set<PolicySubObject> policySubObjects) {
        this.policySubObjects = policySubObjects;
        return this;
    }
}
