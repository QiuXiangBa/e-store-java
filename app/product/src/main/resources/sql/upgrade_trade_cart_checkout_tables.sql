SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for trade_cart
-- ----------------------------
CREATE TABLE IF NOT EXISTS `trade_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物车编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `spu_id` bigint NOT NULL COMMENT '商品 SPU 编号',
  `sku_id` bigint NOT NULL COMMENT '商品 SKU 编号',
  `quantity` int NOT NULL COMMENT '数量',
  `selected` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否勾选: 0 未勾选 1 已勾选',
  `sku_price` int NOT NULL COMMENT 'SKU 快照价格(分)',
  `spu_name` varchar(128) NOT NULL COMMENT 'SPU 名称快照',
  `sku_pic_url` varchar(256) DEFAULT NULL COMMENT 'SKU 图片快照',
  `sku_properties` varchar(512) DEFAULT NULL COMMENT 'SKU 规格文案快照',
  `creator` varchar(64) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `tenant_id` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_sku` (`user_id`, `sku_id`, `tenant_id`, `deleted`),
  KEY `idx_user_selected` (`user_id`, `selected`, `tenant_id`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='购物车';

-- ----------------------------
-- Table structure for trade_checkout_order
-- ----------------------------
CREATE TABLE IF NOT EXISTS `trade_checkout_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '结算单编号',
  `order_no` varchar(64) NOT NULL COMMENT '结算单号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `status` int NOT NULL COMMENT '状态: 0 已创建',
  `item_count` int NOT NULL COMMENT '商品项数',
  `total_amount` int NOT NULL COMMENT '总金额(分)',
  `pay_amount` int NOT NULL COMMENT '应付金额(分)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `tenant_id` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`, `tenant_id`, `deleted`),
  KEY `idx_user_ctime` (`user_id`, `create_time`, `tenant_id`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='结算单';

-- ----------------------------
-- Table structure for trade_checkout_item
-- ----------------------------
CREATE TABLE IF NOT EXISTS `trade_checkout_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '结算明细编号',
  `checkout_order_id` bigint NOT NULL COMMENT '结算单编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `spu_id` bigint NOT NULL COMMENT '商品 SPU 编号',
  `sku_id` bigint NOT NULL COMMENT '商品 SKU 编号',
  `spu_name` varchar(128) NOT NULL COMMENT 'SPU 名称快照',
  `sku_pic_url` varchar(256) DEFAULT NULL COMMENT 'SKU 图片快照',
  `sku_properties` varchar(512) DEFAULT NULL COMMENT 'SKU 规格文案快照',
  `price` int NOT NULL COMMENT '单价(分)',
  `quantity` int NOT NULL COMMENT '数量',
  `line_amount` int NOT NULL COMMENT '行金额(分)',
  `creator` varchar(64) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `tenant_id` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`checkout_order_id`, `tenant_id`, `deleted`),
  KEY `idx_user_ctime` (`user_id`, `create_time`, `tenant_id`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='结算单明细';

SET FOREIGN_KEY_CHECKS = 1;
