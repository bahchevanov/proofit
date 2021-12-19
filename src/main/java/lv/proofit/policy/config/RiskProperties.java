package lv.proofit.policy.config;

import lv.proofit.policy.domain.Risk;

import java.util.HashMap;
import java.util.Map;

/**
 * A RiskProperties
 * loads various risk implementations and default risk implementation as well
 * Properties are configured in the {@code application.yml} file.
 */
public class RiskProperties {

    private final Map<String, Risk> risks = new HashMap<>();
    private final Risk defaultRisk = new Risk();
    private Integer premiumScale;

    /**
     *
     * @return default risk implementation
     */
    public Risk getDefaultRisk() {
        return defaultRisk;
    }

    /**
     *
     * @return a map with type specific risk implementations
     */
    public Map<String, Risk> getRisks() {
        return risks;
    }

    /**
     * elects risk based on the type or the default one if type is not found in the map
     * @param riskType type of the risk
     * @return a risk implementation
     */
    public Risk getRiskByTypeOrDefault(String riskType) {
        return risks.getOrDefault(riskType, getDefaultRisk());
    }

    /**
     *
     * @return the scale for rounding up the premium value
     */
    public Integer getPremiumScale() {
        return premiumScale;
    }

    public void setPremiumScale(Integer premiumScale) {
        this.premiumScale = premiumScale;
    }
}
