package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户收货地址 / Trade user address.
 */
@Data
@TableName(value = "trade_user_address")
public class TradeUserAddress {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "receiver_name")
    private String receiverName;

    @TableField(value = "receiver_mobile")
    private String receiverMobile;

    @TableField(value = "province_code")
    private String provinceCode;

    @TableField(value = "province_name")
    private String provinceName;

    @TableField(value = "city_code")
    private String cityCode;

    @TableField(value = "city_name")
    private String cityName;

    @TableField(value = "district_code")
    private String districtCode;

    @TableField(value = "district_name")
    private String districtName;

    @TableField(value = "detail_address")
    private String detailAddress;

    @TableField(value = "postal_code")
    private String postalCode;

    @TableField(value = "is_default")
    private Integer isDefault;

    @TableField(value = "status")
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
    private Short deleted;

    @TableField(value = "tenant_id")
    private Long tenantId;
}
