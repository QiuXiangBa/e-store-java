package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * SPU 展示属性值 / SPU display property value.
 */
@Data
@TableName(value = "product_spu_property")
public class ProductSpuProperty {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "spu_id")
    private Long spuId;

    @TableField(value = "property_id")
    private Long propertyId;

    @TableField(value = "property_name")
    private String propertyName;

    @TableField(value = "value_text")
    private String valueText;

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
