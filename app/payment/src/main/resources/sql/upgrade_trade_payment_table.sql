-- 支付单表 / Trade payment table
-- Issue: e-store-java#18 支付闭环（Stripe）
CREATE TABLE IF NOT EXISTS `trade_payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `checkout_order_id` bigint NOT NULL COMMENT '结算单ID',
  `stripe_payment_intent_id` varchar(64) NOT NULL COMMENT 'Stripe PaymentIntent ID',
  `amount` int NOT NULL COMMENT '支付金额（分）',
  `currency` varchar(8) NOT NULL DEFAULT 'usd' COMMENT '货币代码',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态：0-待支付 1-成功 2-失败 3-已取消',
  `paid_at` timestamp NULL DEFAULT NULL COMMENT '支付成功时间',
  `creator` varchar(64) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` smallint NOT NULL DEFAULT 0,
  `tenant_id` bigint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_checkout_order` (`checkout_order_id`, `tenant_id`, `deleted`),
  UNIQUE KEY `uk_stripe_pi` (`stripe_payment_intent_id`, `tenant_id`, `deleted`),
  KEY `idx_checkout_order_id` (`checkout_order_id`, `tenant_id`, `deleted`),
  KEY `idx_stripe_pi_id` (`stripe_payment_intent_id`, `tenant_id`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付单';
