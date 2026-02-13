/*
 Navicat Premium Dump SQL

 Source Server         : local-12345678
 Source Server Type    : MySQL
 Source Server Version : 90300 (9.3.0)
 Source Host           : localhost:3306
 Source Schema         : e-store

 Target Server Type    : MySQL
 Target Server Version : 90300 (9.3.0)
 File Encoding         : 65001

 Date: 12/02/2026 17:41:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product_brand
-- ----------------------------
DROP TABLE IF EXISTS `product_brand`;
CREATE TABLE `product_brand` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '品牌编号',
                                 `name` varchar(255) NOT NULL COMMENT '品牌名称',
                                 `pic_url` varchar(255) NOT NULL COMMENT '品牌图片',
                                 `sort` int DEFAULT '0' COMMENT '品牌排序',
                                 `description` varchar(1024) DEFAULT NULL COMMENT '品牌描述',
                                 `status` int NOT NULL COMMENT '状态',
                                 `creator` varchar(64) DEFAULT '',
                                 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 `updater` varchar(64) DEFAULT '',
                                 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 `deleted` tinyint(1) NOT NULL DEFAULT '0',
                                 `tenant_id` bigint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品品牌';

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类编号',
                                    `parent_id` bigint NOT NULL COMMENT '父分类编号',
                                    `name` varchar(255) NOT NULL COMMENT '分类名称',
                                    `pic_url` varchar(255) NOT NULL COMMENT '移动端分类图',
                                    `big_pic_url` varchar(255) DEFAULT NULL COMMENT 'PC 端分类图',
                                    `sort` int DEFAULT '0' COMMENT '分类排序',
                                    `status` int NOT NULL COMMENT '开启状态',
                                    `creator` varchar(64) DEFAULT '',
                                    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `updater` varchar(64) DEFAULT '',
                                    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `deleted` tinyint(1) NOT NULL DEFAULT '0',
                                    `tenant_id` bigint NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品分类';

-- ----------------------------
-- Table structure for product_comment
-- ----------------------------
DROP TABLE IF EXISTS `product_comment`;
CREATE TABLE `product_comment` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论编号，主键自增',
                                   `user_id` bigint DEFAULT NULL COMMENT '评价人的用户编号关联 MemberUserDO 的 id 编号',
                                   `user_nickname` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评价人名称',
                                   `user_avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '评价人头像',
                                   `anonymous` bit(1) DEFAULT NULL COMMENT '是否匿名',
                                   `order_id` bigint DEFAULT NULL COMMENT '交易订单编号关联 TradeOrderDO 的 id 编号',
                                   `order_item_id` bigint DEFAULT NULL COMMENT '交易订单项编号关联 TradeOrderItemDO 的 id 编号',
                                   `spu_id` bigint DEFAULT NULL COMMENT '商品 SPU 编号关联 ProductSpuDO 的 id',
                                   `spu_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品 SPU 名称',
                                   `sku_id` bigint DEFAULT NULL COMMENT '商品 SKU 编号关联 ProductSkuDO 的 id 编号',
                                   `visible` tinyint(1) DEFAULT NULL COMMENT '是否可见true:显示false:隐藏',
                                   `scores` int DEFAULT NULL COMMENT '评分星级1-5分',
                                   `description_scores` int DEFAULT NULL COMMENT '描述星级1-5 星',
                                   `benefit_scores` int DEFAULT NULL COMMENT '服务星级1-5 星',
                                   `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '评论内容',
                                   `pic_urls` varchar(4096) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评论图片地址数组',
                                   `reply_status` int DEFAULT NULL COMMENT '商家是否回复',
                                   `reply_user_id` bigint DEFAULT NULL COMMENT '回复管理员编号关联 AdminUserDO 的 id 编号',
                                   `reply_content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商家回复内容',
                                   `reply_time` datetime DEFAULT NULL COMMENT '商家回复时间',
                                   `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
                                   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
                                   `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品评论';

-- ----------------------------
-- Table structure for product_property
-- ----------------------------
DROP TABLE IF EXISTS `product_property`;
CREATE TABLE `product_property` (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `name` varchar(64) DEFAULT NULL COMMENT '规格名称',
                                    `status` int DEFAULT NULL COMMENT '状态： 0 开启 ，1 禁用',
                                    `creator` varchar(64) DEFAULT '',
                                    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `updater` varchar(64) DEFAULT '',
                                    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `deleted` tinyint(1) NOT NULL DEFAULT '0',
                                    `tenant_id` bigint NOT NULL DEFAULT '0',
                                    `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='规格名称';

-- ----------------------------
-- Table structure for product_property_value
-- ----------------------------
DROP TABLE IF EXISTS `product_property_value`;
CREATE TABLE `product_property_value` (
                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                          `property_id` bigint DEFAULT NULL COMMENT '规格键id',
                                          `name` varchar(128) DEFAULT NULL COMMENT '规格值名字',
                                          `status` int DEFAULT NULL COMMENT '状态： 0 开启 ，1 禁用',
                                          `creator` varchar(64) DEFAULT '',
                                          `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          `updater` varchar(64) DEFAULT '',
                                          `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          `deleted` tinyint(1) NOT NULL DEFAULT '0',
                                          `tenant_id` bigint NOT NULL DEFAULT '0',
                                          `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='规格值';

-- ----------------------------
-- Table structure for product_sku
-- ----------------------------
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `spu_id` bigint NOT NULL COMMENT 'spu编号',
                               `properties` varchar(512) DEFAULT NULL COMMENT '属性数组，JSON 格式',
                               `price` int NOT NULL DEFAULT '-1' COMMENT '商品价格，单位：分',
                               `market_price` int DEFAULT NULL COMMENT '市场价，单位：分',
                               `cost_price` int NOT NULL DEFAULT '-1' COMMENT '成本价，单位： 分',
                               `bar_code` varchar(64) DEFAULT NULL COMMENT 'SKU 的条形码',
                               `pic_url` varchar(256) NOT NULL COMMENT '图片地址',
                               `stock` int DEFAULT NULL COMMENT '库存',
                               `weight` double DEFAULT NULL COMMENT '商品重量，单位：kg 千克',
                               `volume` double DEFAULT NULL COMMENT '商品体积，单位：m^3 平米',
                               `sub_commission_first_price` int DEFAULT NULL COMMENT '一级分销的佣金，单位：分',
                               `sub_commission_second_price` int DEFAULT NULL COMMENT '二级分销的佣金，单位：分',
                               `sales_count` int DEFAULT NULL COMMENT '商品销量',
                               `creator` varchar(64) DEFAULT '',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `updater` varchar(64) DEFAULT '',
                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `deleted` tinyint NOT NULL DEFAULT '0',
                               `tenant_id` bigint NOT NULL DEFAULT '0',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='商品sku';

-- ----------------------------
-- Table structure for product_spu
-- ----------------------------
DROP TABLE IF EXISTS `product_spu`;
CREATE TABLE `product_spu` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品 SPU 编号，自增',
                               `name` varchar(128) NOT NULL COMMENT '商品名称',
                               `keyword` varchar(256) NOT NULL COMMENT '关键字',
                               `introduction` varchar(256) NOT NULL COMMENT '商品简介',
                               `description` text NOT NULL COMMENT '商品详情',
                               `bar_code` varchar(64) NOT NULL COMMENT '条形码',
                               `category_id` bigint NOT NULL COMMENT '商品分类编号',
                               `brand_id` int DEFAULT NULL COMMENT '商品品牌编号',
                               `pic_url` varchar(256) NOT NULL COMMENT '商品封面图',
                               `slider_pic_urls` varchar(2000) DEFAULT '' COMMENT '商品轮播图地址\n 数组，以逗号分隔\n 最多上传15张',
                               `video_url` varchar(256) DEFAULT NULL COMMENT '商品视频',
                               `sort` int NOT NULL DEFAULT '0' COMMENT '排序字段',
                               `status` int NOT NULL COMMENT '商品状态: 0 上架（开启） 1 下架（禁用）-1 回收',
                               `spec_type` int NOT NULL COMMENT '规格类型：0 单规格 1 多规格',
                               `price` int NOT NULL DEFAULT '-1' COMMENT '商品价格，单位使用：分',
                               `market_price` int NOT NULL COMMENT '市场价，单位使用：分',
                               `cost_price` int NOT NULL DEFAULT '-1' COMMENT '成本价，单位： 分',
                               `stock` int NOT NULL DEFAULT '0' COMMENT '库存',
                               `delivery_template_id` bigint NOT NULL COMMENT '物流配置模板编号',
                               `recommend_hot` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否热卖推荐: 0 默认 1 热卖',
                               `recommend_benefit` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否优惠推荐: 0 默认 1 优选',
                               `recommend_best` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否精品推荐: 0 默认 1 精品',
                               `recommend_new` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否新品推荐: 0 默认 1 新品',
                               `recommend_good` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否优品推荐',
                               `give_integral` int NOT NULL COMMENT '赠送积分',
                               `give_coupon_template_ids` varchar(512) DEFAULT '' COMMENT '赠送的优惠劵编号的数组',
                               `sub_commission_type` int NOT NULL COMMENT '分销类型',
                               `activity_orders` varchar(16) NOT NULL DEFAULT '' COMMENT '活动显示排序0=默认, 1=秒杀，2=砍价，3=拼团',
                               `sales_count` int DEFAULT '0' COMMENT '商品销量',
                               `virtual_sales_count` int DEFAULT '0' COMMENT '虚拟销量',
                               `browse_count` int DEFAULT '0' COMMENT '商品点击量',
                               `creator` varchar(64) DEFAULT '',
                               `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `updater` varchar(64) DEFAULT '',
                               `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `deleted` tinyint(1) NOT NULL DEFAULT '0',
                               `tenant_id` bigint NOT NULL DEFAULT '0',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='商品spu';
-- ----------------------------
-- Table structure for system_oauth2_access_token
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_access_token`;
CREATE TABLE `system_oauth2_access_token` (
                                              `id` bigint NOT NULL AUTO_INCREMENT,
                                              `user_id` bigint NOT NULL,
                                              `user_type` smallint NOT NULL,
                                              `user_info` varchar(512) NOT NULL,
                                              `access_token` varchar(255) NOT NULL,
                                              `refresh_token` varchar(32) NOT NULL,
                                              `client_id` varchar(255) NOT NULL,
                                              `scopes` varchar(255) DEFAULT NULL,
                                              `expires_time` timestamp NOT NULL,
                                              `creator` varchar(64) DEFAULT '',
                                              `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              `updater` varchar(64) DEFAULT '',
                                              `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              `deleted` smallint NOT NULL DEFAULT '0',
                                              `tenant_id` bigint NOT NULL DEFAULT '0',
                                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for system_oauth2_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_refresh_token`;
CREATE TABLE `system_oauth2_refresh_token` (
                                               `id` bigint NOT NULL AUTO_INCREMENT,
                                               `user_id` bigint NOT NULL,
                                               `refresh_token` varchar(32) NOT NULL,
                                               `user_type` smallint NOT NULL,
                                               `client_id` varchar(255) NOT NULL,
                                               `scopes` varchar(255) DEFAULT NULL,
                                               `expires_time` timestamp NOT NULL,
                                               `creator` varchar(64) DEFAULT '',
                                               `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                               `updater` varchar(64) DEFAULT '',
                                               `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                               `deleted` smallint NOT NULL DEFAULT '0',
                                               `tenant_id` bigint NOT NULL DEFAULT '0',
                                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for system_users
-- ----------------------------
DROP TABLE IF EXISTS `system_users`;
CREATE TABLE `system_users` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `username` varchar(30) NOT NULL,
                                `password` varchar(100) NOT NULL DEFAULT '',
                                `nickname` varchar(30) NOT NULL,
                                `remark` varchar(500) DEFAULT NULL,
                                `dept_id` bigint DEFAULT NULL,
                                `post_ids` varchar(255) DEFAULT NULL,
                                `email` varchar(50) DEFAULT '',
                                `mobile` varchar(11) DEFAULT '',
                                `sex` smallint DEFAULT '0',
                                `avatar` varchar(512) DEFAULT '',
                                `status` smallint NOT NULL DEFAULT '0',
                                `login_ip` varchar(50) DEFAULT '',
                                `login_date` timestamp NULL DEFAULT NULL,
                                `creator` varchar(64) DEFAULT '',
                                `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `updater` varchar(64) DEFAULT '',
                                `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `deleted` smallint NOT NULL DEFAULT '0',
                                `tenant_id` bigint NOT NULL DEFAULT '0',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
