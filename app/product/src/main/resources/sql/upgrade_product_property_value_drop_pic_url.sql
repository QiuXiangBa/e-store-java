-- product_property_value 移除 pic_url 字段
-- Drop legacy pic_url column from product_property_value.
ALTER TABLE `product_property_value`
    DROP COLUMN `pic_url`;
