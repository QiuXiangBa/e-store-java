package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "system_users")
public class SystemUsers {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "`password`")
    private String password;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "dept_id")
    private Long deptId;

    @TableField(value = "post_ids")
    private String postIds;

    @TableField(value = "email")
    private String email;

    @TableField(value = "mobile")
    private String mobile;

    @TableField(value = "sex")
    private Short sex;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "`status`")
    private Short status;

    @TableField(value = "login_ip")
    private String loginIp;

    @TableField(value = "login_date")
    private Date loginDate;

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