SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `trade_stock_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '库存流水编号',
  `biz_type` int NOT NULL COMMENT '业务类型: 1订单创建扣减 2订单取消回补 3订单关闭回补',
  `biz_no` varchar(64) NOT NULL COMMENT '业务单号(通常为 order_no)',
  `order_id` bigint DEFAULT NULL COMMENT '订单ID',
  `sku_id` bigint NOT NULL COMMENT 'SKU ID',
  `change_qty` int NOT NULL COMMENT '变更数量(正数=回补,负数=扣减)',
  `change_reason` varchar(128) DEFAULT NULL COMMENT '变更原因',
  `creator` varchar(64) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `tenant_id` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_biz_sku` (`biz_type`,`biz_no`,`sku_id`,`tenant_id`,`deleted`),
  KEY `idx_order` (`order_id`,`tenant_id`,`deleted`),
  KEY `idx_sku_ctime` (`sku_id`,`create_time`,`tenant_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存变更流水';

SET FOREIGN_KEY_CHECKS = 1;
