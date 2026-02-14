package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 规格名称
 */
@Data
@TableName(value = "product_property")
public class ProductProperty {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 规格名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 属性类型：0 展示属性，1 销售属性 / Property type: 0 display, 1 sales.
     */
    @TableField(value = "property_type")
    private Integer propertyType;

    /**
     * 录入类型：0 手工输入，1 预设值选择 / Input type: 0 manual, 1 select.
     */
    @TableField(value = "input_type")
    private Integer inputType;

    /**
     * 状态： 0 开启 ，1 禁用
     */
    @TableField(value = "`status`")
    private Integer status;

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

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}
