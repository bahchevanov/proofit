package lv.proofit.policy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    @JsonIgnoreProperties(value = {"policyObject"}, allowSetters = true)
    private Set<PolicySubObject> policySubObjects = new HashSet<>();

    @JsonIgnoreProperties(value = {"policyObjects"}, allowSetters = true)
    private Policy policy;

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

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
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
}
