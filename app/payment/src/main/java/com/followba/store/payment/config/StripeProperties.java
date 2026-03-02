package com.followba.store.payment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Stripe 配置 / Stripe configuration properties.
 */
@Data
@ConfigurationProperties(prefix = "stripe")
public class StripeProperties {

    /**
     * Stripe 密钥 / Stripe secret key.
     */
    private String secretKey;

    /**
     * Webhook 签名密钥 / Webhook signing secret.
     */
    private String webhookSecret;

    /**
     * 默认货币 / Default currency (e.g. usd).
     */
    private String currency = "usd";
}
