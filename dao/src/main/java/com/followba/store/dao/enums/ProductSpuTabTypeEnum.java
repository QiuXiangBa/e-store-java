package com.followba.store.dao.enums;

import lombok.Getter;

/**
 * 商品 SPU 列表 Tab 枚举 / Product SPU list tab enum.
 */
@Getter
public enum ProductSpuTabTypeEnum {

    /**
     * 出售中 / For sale.
     */
    FOR_SALE(0),

    /**
     * 仓库中 / In warehouse.
     */
    IN_WAREHOUSE(1),

    /**
     * 已售罄 / Sold out.
     */
    SOLD_OUT(2),

    /**
     * 警戒库存 / Alert stock.
     */
    ALERT_STOCK(3),

    /**
     * 回收站 / Recycle bin.
     */
    RECYCLE_BIN(4);

    /**
     * Tab 值 / Tab code.
     */
    private final int code;

    ProductSpuTabTypeEnum(int code) {
        this.code = code;
    }
}
