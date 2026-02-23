ALTER TABLE `product_spu`
ADD COLUMN `material_pic_urls` varchar(2000) DEFAULT '' COMMENT '3:4 主图素材地址数组(JSON)' AFTER `slider_pic_urls`;

