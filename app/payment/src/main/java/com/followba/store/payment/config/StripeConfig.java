package com.followba.store.payment.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Stripe 初始化配置 / Stripe initialization config.
 */
@Configuration
@EnableConfigurationProperties(StripeProperties.class)
public class StripeConfig {

    private final StripeProperties properties;

    public StripeConfig(StripeProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        if (properties.getSecretKey() != null && !properties.getSecretKey().isBlank()) {
            Stripe.apiKey = properties.getSecretKey();
        }
    }
}
