-- 商品分类升级：新增 is_leaf + path，并清理开发阶段脏数据
-- Product category upgrade: add is_leaf + path and clear dirty data in development stage.

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE `product_category`
    ADD COLUMN IF NOT EXISTS `is_leaf` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否叶子类目' AFTER `sort`,
    ADD COLUMN IF NOT EXISTS `path` varchar(512) NOT NULL DEFAULT '' COMMENT '类目路径，格式：/1/2/3/' AFTER `is_leaf`;

ALTER TABLE `product_category`
    DROP INDEX IF EXISTS `idx_parent`,
    DROP INDEX IF EXISTS `idx_leaf_status`;

ALTER TABLE `product_category`
    ADD INDEX `idx_parent` (`parent_id`, `deleted`),
    ADD INDEX `idx_leaf_status` (`is_leaf`, `status`, `deleted`);

-- 开发阶段不兼容旧数据，直接清理类目及类目属性绑定
-- In development, clean category and category-property binding data directly.
DELETE FROM `product_category_property`;
DELETE FROM `product_category`;
ALTER TABLE `product_category` AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;
