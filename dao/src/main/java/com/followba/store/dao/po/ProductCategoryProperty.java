package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 商品类目属性绑定 / Product category property binding.
 */
@Data
@TableName(value = "product_category_property")
public class ProductCategoryProperty {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "category_id")
    private Long categoryId;

    @TableField(value = "property_id")
    private Long propertyId;

    @TableField(value = "enabled")
    private Boolean enabled;

    @TableField(value = "required")
    private Boolean required;

    /**
     * 是否支持规格值配图 / Whether property values support image configuration.
     */
    @TableField(value = "support_value_image")
    private Boolean supportValueImage;

    /**
     * 规格值图片是否必填 / Whether value image is required.
     */
    @TableField(value = "value_image_required")
    private Boolean valueImageRequired;

    @TableField(value = "sort")
    private Integer sort;

    @TableField(value = "creator")
    private String creator;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "updater")
    private String updater;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "deleted")
    private Boolean deleted;

    @TableField(value = "tenant_id")
    private Long tenantId;
}
