package lv.proofit.policy.config;

import lv.proofit.policy.domain.Risk;

/**
 * A RiskProperties
 */
public class RiskProperties {

    private final Risk fireRisk = new Risk();
    private final Risk theftRisk = new Risk();
    private final Risk defaultRisk = new Risk();
    private Integer premiumScale;

    public Risk getFireRisk() {
        return fireRisk;
    }

    public Risk getTheftRisk() {
        return theftRisk;
    }

    public Integer getPremiumScale() {
        return premiumScale;
    }

    public void setPremiumScale(Integer premiumScale) {
        this.premiumScale = premiumScale;
    }

    public Risk getDefaultRisk() {
        return defaultRisk;
    }
}
