package com.followba.store.admin.vo.out;

import lombok.Data;

@Data
public class CommonIdRespVO {

    private Long id;

    public static CommonIdRespVO of(Long id) {
        CommonIdRespVO resp = new CommonIdRespVO();
        resp.setId(id);
        return resp;
    }
}
