package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 规格值
 */
@Data
@TableName(value = "product_property_value")
public class ProductPropertyValue {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 规格键id
     */
    @TableField(value = "property_id")
    private Long propertyId;

    /**
     * 规格值名字
     */
    @TableField(value = "`name`")
    private String name;

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
