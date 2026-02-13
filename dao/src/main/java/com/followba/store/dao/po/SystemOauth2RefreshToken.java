package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "system_oauth2_refresh_token")
public class SystemOauth2RefreshToken {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "refresh_token")
    private String refreshToken;

    @TableField(value = "user_type")
    private Short userType;

    @TableField(value = "client_id")
    private String clientId;

    @TableField(value = "scopes")
    private String scopes;

    @TableField(value = "expires_time")
    private Date expiresTime;

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