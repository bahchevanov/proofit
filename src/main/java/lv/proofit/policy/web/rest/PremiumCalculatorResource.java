package lv.proofit.policy.web.rest;

import lv.proofit.policy.domain.Policy;
import lv.proofit.policy.service.PremiumCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller for premium calculation of a {@link lv.proofit.policy.domain.Policy}.
 */
@RestController
@RequestMapping("/api")
public class PremiumCalculatorResource {

    private final Logger log = LoggerFactory.getLogger(PremiumCalculatorResource.class);

    private final PremiumCalculator calculator;

    public PremiumCalculatorResource(PremiumCalculator calculator) {
        this.calculator = calculator;
    }

    /**
     * {@code POST  /calculate} : Calculates a Policy premium
     *
     * @param policy the policy to calculate premium.
     * @return the {@link ResponseEntity} with status {@code 200 (Created)} and with the calculated premium
     */
    @PostMapping("/calculate")
    public ResponseEntity<Double> calculatePremium(@Valid @RequestBody Policy policy) {
        log.debug("REST request to calculate premium of Policy : {}", policy);

        Double premium = calculator.calculate(policy);

        return ResponseEntity.ok(premium);
    }
}
