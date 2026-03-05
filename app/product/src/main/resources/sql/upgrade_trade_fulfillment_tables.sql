SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 严格模式：重建履约表结构，不保留旧履约数据。 / Strict mode: rebuild fulfillment tables, no legacy fulfillment compatibility.
DROP TABLE IF EXISTS `trade_fulfillment_log`;
DROP TABLE IF EXISTS `trade_fulfillment`;

-- ----------------------------
-- Table structure for trade_fulfillment
-- ----------------------------
CREATE TABLE `trade_fulfillment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '履约单ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `status` int NOT NULL COMMENT '履约状态: 0待发货 10已发货 20运输中 30派送中 40已签收 50已关闭',
  `logistics_company_code` varchar(64) DEFAULT '' COMMENT '物流公司编码',
  `logistics_company_name` varchar(64) DEFAULT '' COMMENT '物流公司名称',
  `tracking_no` varchar(128) DEFAULT '' COMMENT '运单号',
  `latest_node` varchar(255) DEFAULT '' COMMENT '最新物流节点描述',
  `shipped_time` datetime DEFAULT NULL COMMENT '发货时间',
  `signed_time` datetime DEFAULT NULL COMMENT '签收时间',
  `closed_time` datetime DEFAULT NULL COMMENT '履约关闭时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
  `creator` varchar(64) DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `tenant_id` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id_tenant_deleted` (`order_id`,`tenant_id`,`deleted`),
  UNIQUE KEY `uk_order_no_tenant_deleted` (`order_no`,`tenant_id`,`deleted`),
  KEY `idx_user_status_ctime` (`user_id`,`status`,`create_time`,`tenant_id`,`deleted`),
  KEY `idx_tracking_no` (`tracking_no`,`tenant_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单履约主表';

-- ----------------------------
-- Table structure for trade_fulfillment_log
-- ----------------------------
CREATE TABLE `trade_fulfillment_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '履约日志ID',
  `fulfillment_id` bigint NOT NULL COMMENT '履约单ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `from_status` int DEFAULT NULL COMMENT '变更前履约状态',
  `to_status` int NOT NULL COMMENT '变更后履约状态',
  `operate_type` int NOT NULL COMMENT '操作类型: 0创建 1发货 2物流节点 3签收 4关闭',
  `operator_type` int NOT NULL COMMENT '操作者类型: 0管理员 1用户 2系统',
  `operator_id` bigint DEFAULT NULL COMMENT '操作者ID',
  `node_desc` varchar(255) DEFAULT '' COMMENT '物流节点描述',
  `node_time` datetime DEFAULT NULL COMMENT '节点时间',
  `creator` varchar(64) DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updater` varchar(64) DEFAULT '',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `tenant_id` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_fulfillment_ctime` (`fulfillment_id`,`create_time`,`tenant_id`,`deleted`),
  KEY `idx_order_ctime` (`order_id`,`create_time`,`tenant_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单履约日志表';

-- 历史订单按新规则初始化履约主记录。 / Initialize fulfillment records for historical orders under new rules.
INSERT INTO `trade_fulfillment` (
  `order_id`, `order_no`, `user_id`, `status`,
  `latest_node`, `shipped_time`, `signed_time`, `closed_time`,
  `creator`, `updater`, `deleted`, `tenant_id`
)
SELECT
  t.`id`,
  t.`order_no`,
  t.`user_id`,
  CASE
    WHEN t.`status` = 10 THEN 0
    WHEN t.`status` = 40 THEN 40
    WHEN t.`status` IN (20, 30) THEN 50
    ELSE 0
  END AS `status`,
  CASE
    WHEN t.`status` = 10 THEN '支付成功，待发货'
    WHEN t.`status` = 40 THEN '订单已签收'
    WHEN t.`status` IN (20, 30) THEN '订单已关闭'
    ELSE ''
  END AS `latest_node`,
  CASE
    WHEN t.`status` = 40 THEN COALESCE(t.`paid_time`, t.`update_time`)
    ELSE NULL
  END AS `shipped_time`,
  CASE
    WHEN t.`status` = 40 THEN COALESCE(t.`update_time`, t.`paid_time`)
    ELSE NULL
  END AS `signed_time`,
  CASE
    WHEN t.`status` IN (20, 30) THEN COALESCE(t.`close_time`, t.`cancel_time`, t.`update_time`)
    ELSE NULL
  END AS `closed_time`,
  'migration',
  'migration',
  0,
  t.`tenant_id`
FROM `trade_order` t
WHERE t.`deleted` = 0
  AND t.`status` IN (10, 20, 30, 40);

-- 初始化一条当前状态日志，确保链路可追踪。 / Seed one status log row for each initialized fulfillment.
INSERT INTO `trade_fulfillment_log` (
  `fulfillment_id`, `order_id`, `from_status`, `to_status`,
  `operate_type`, `operator_type`, `operator_id`,
  `node_desc`, `node_time`, `creator`, `updater`, `deleted`, `tenant_id`
)
SELECT
  f.`id`,
  f.`order_id`,
  NULL,
  f.`status`,
  CASE
    WHEN f.`status` = 0 THEN 0
    WHEN f.`status` = 40 THEN 3
    WHEN f.`status` = 50 THEN 4
    ELSE 0
  END AS `operate_type`,
  2,
  0,
  f.`latest_node`,
  COALESCE(f.`signed_time`, f.`closed_time`, f.`create_time`),
  'migration',
  'migration',
  0,
  f.`tenant_id`
FROM `trade_fulfillment` f
WHERE f.`deleted` = 0;

SET FOREIGN_KEY_CHECKS = 1;
