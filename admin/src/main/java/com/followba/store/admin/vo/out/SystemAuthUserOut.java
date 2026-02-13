package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.Date;

@Data
public class SystemAuthUserOut {

    private Long id;

    private String username;

    private String nickname;

    private Long deptId;

    private String email;

    private String mobile;

    private Short sex;

    private String avatar;

    private String loginIp;

    private Date loginDate;
}
