package com.followba.store.product.service;

import com.followba.store.common.resp.PageResp;
import com.followba.store.product.dto.ProductAppSpuDTO;
import com.followba.store.product.dto.ProductAppSpuDetailDTO;
import com.followba.store.product.dto.ProductPageQueryDTO;

public interface MallProductService {

    PageResp<ProductAppSpuDTO> page(ProductPageQueryDTO queryDTO);

    ProductAppSpuDetailDTO detail(Long spuId);
}
