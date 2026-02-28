package com.followba.store.product.vo.out;

import lombok.Data;

@Data
public class CommonIdOut {

    private Long id;

    public static CommonIdOut of(Long id) {
        CommonIdOut out = new CommonIdOut();
        out.setId(id);
        return out;
    }
}
