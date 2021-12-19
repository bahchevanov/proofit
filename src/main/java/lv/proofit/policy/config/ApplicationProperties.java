package lv.proofit.policy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to Proof It.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final CorsConfiguration cors = new CorsConfiguration();

    private final RiskProperties riskProperties = new RiskProperties();

    public CorsConfiguration getCors() {
        return this.cors;
    }

    public RiskProperties getRiskProperties() {
        return riskProperties;
    }
}
