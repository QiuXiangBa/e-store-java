package com.followba.store.auth.vo.out;

import lombok.Data;

@Data
public class CommonBooleanOut {

    private Boolean success;

    public static CommonBooleanOut ok() {
        CommonBooleanOut out = new CommonBooleanOut();
        out.setSuccess(Boolean.TRUE);
        return out;
    }
}
