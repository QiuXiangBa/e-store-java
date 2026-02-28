SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for trade_order
-- ----------------------------
CREATE TABLE IF NOT EXISTS `trade_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `request_id` varchar(64) NOT NULL COMMENT '幂等请求标识',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `checkout_order_id` bigint DEFAULT NULL COMMENT '关联结算单编号',
  `status` int NOT NULL COMMENT '订单状态',
  `item_count` int NOT NULL DEFAULT '0' COMMENT '商品件数',
  `total_amount` int NOT NULL DEFAULT '0' COMMENT '总金额(分)',
  `pay_amount` int NOT NULL DEFAULT '0' COMMENT '应付金额(分)',
  `remark` varchar(255) DEFAULT NULL COMMENT '用户备注',
  `cancel_reason` varchar(255) DEFAULT NULL COMMENT '取消原因',
  `paid_time` timestamp NULL DEFAULT NULL COMMENT '支付完成时间',
  `cancel_time` timestamp NULL DEFAULT NULL COMMENT '取消时间',
  `close_time` timestamp NULL DEFAULT NULL COMMENT '关闭时间',
  `creator` varchar(64) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `tenant_id` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`,`tenant_id`,`deleted`),
  UNIQUE KEY `uk_user_request` (`user_id`,`request_id`,`tenant_id`,`deleted`),
  KEY `idx_checkout_order_id` (`checkout_order_id`,`tenant_id`,`deleted`),
  KEY `idx_user_ctime` (`user_id`,`create_time`,`tenant_id`,`deleted`),
  KEY `idx_status_ctime` (`status`,`create_time`,`tenant_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='交易订单主表';

-- ----------------------------
-- Table structure for trade_order_item
-- ----------------------------
CREATE TABLE IF NOT EXISTS `trade_order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单项编号',
  `order_id` bigint NOT NULL COMMENT '订单编号',
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
  KEY `idx_order_id` (`order_id`,`tenant_id`,`deleted`),
  KEY `idx_user_id` (`user_id`,`tenant_id`,`deleted`),
  KEY `idx_sku_id` (`sku_id`,`tenant_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='交易订单项快照表';

-- ----------------------------
-- Table structure for trade_order_operate_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `trade_order_operate_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `order_id` bigint NOT NULL COMMENT '订单编号',
  `from_status` int DEFAULT NULL COMMENT '变更前状态',
  `to_status` int NOT NULL COMMENT '变更后状态',
  `operate_type` int NOT NULL COMMENT '操作类型',
  `operator_type` int NOT NULL COMMENT '操作者类型',
  `operator_id` bigint DEFAULT NULL COMMENT '操作者编号',
  `reason` varchar(255) DEFAULT NULL COMMENT '原因',
  `creator` varchar(64) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `tenant_id` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`,`tenant_id`,`deleted`),
  KEY `idx_op_type_ctime` (`operate_type`,`create_time`,`tenant_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='交易订单操作日志表';

SET FOREIGN_KEY_CHECKS = 1;
