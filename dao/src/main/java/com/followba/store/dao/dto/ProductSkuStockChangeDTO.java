package com.followba.store.dao.dto;

import lombok.Data;

/**
 * SKU 库存变更 DTO / SKU stock change DTO.
 */
@Data
public class ProductSkuStockChangeDTO {

    /**
     * SKU 编号 / SKU id.
     */
    private Long skuId;

    /**
     * 变更数量（正数） / Change quantity (positive number).
     */
    private Integer quantity;
}
