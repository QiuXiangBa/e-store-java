package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * 商品sku
 */
@Data
@TableName(value = "product_sku", autoResultMap = true)
public class ProductSku {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * spu编号
     */
    @TableField(value = "spu_id")
    private Long spuId;

    /**
     * 属性数组，JSON 格式
     */
    @TableField(value = "properties", typeHandler = JacksonTypeHandler.class)
    private List<Property> properties;

    /**
     * 商品价格，单位：分
     */
    @TableField(value = "price")
    private Integer price;

    /**
     * 市场价，单位：分
     */
    @TableField(value = "market_price")
    private Integer marketPrice;

    /**
     * 成本价，单位： 分
     */
    @TableField(value = "cost_price")
    private Integer costPrice;

    /**
     * SKU 的条形码
     */
    @TableField(value = "bar_code")
    private String barCode;

    /**
     * 图片地址
     */
    @TableField(value = "pic_url")
    private String picUrl;

    /**
     * 库存
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 商品重量，单位：kg 千克
     */
    @TableField(value = "weight")
    private Double weight;

    /**
     * 商品体积，单位：m^3 平米
     */
    @TableField(value = "volume")
    private Double volume;

    /**
     * 一级分销的佣金，单位：分
     */
    @TableField(value = "sub_commission_first_price")
    private Integer subCommissionFirstPrice;

    /**
     * 二级分销的佣金，单位：分
     */
    @TableField(value = "sub_commission_second_price")
    private Integer subCommissionSecondPrice;

    /**
     * 商品销量
     */
    @TableField(value = "sales_count")
    private Integer salesCount;

    @TableField(value = "creator")
    private String creator;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "updater")
    private String updater;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "deleted")
    private Byte deleted;

    @TableField(value = "tenant_id")
    private Long tenantId;

    @Data
    public static class Property {

        private Long propertyId;

        private String propertyName;

        private Long valueId;

        private String valueName;
    }
}
