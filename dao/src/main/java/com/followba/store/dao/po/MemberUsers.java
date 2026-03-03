package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "member_users")
public class MemberUsers {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "`password`")
    private String password;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "email")
    private String email;

    @TableField(value = "mobile")
    private String mobile;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "`status`")
    private Short status;

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
