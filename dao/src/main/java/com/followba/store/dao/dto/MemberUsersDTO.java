package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MemberUsersDTO {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String mobile;

    private String avatar;

    private Short status;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Short deleted;

    private Long tenantId;
}
