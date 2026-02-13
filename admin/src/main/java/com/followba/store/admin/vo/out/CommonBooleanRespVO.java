package com.followba.store.admin.vo.out;

import lombok.Data;

@Data
public class CommonBooleanRespVO {

    private Boolean success;

    public static CommonBooleanRespVO ok() {
        CommonBooleanRespVO resp = new CommonBooleanRespVO();
        resp.setSuccess(true);
        return resp;
    }
}
