package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.ProductSkuSaveIn;
import com.followba.store.dao.dto.ProductSkuDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductSkuService {

    void validateSkuList(List<ProductSkuSaveIn> skus, Boolean specType);

    void createSkuList(Long spuId, List<ProductSkuSaveIn> skus);

    void updateSkuList(Long spuId, List<ProductSkuSaveIn> skus);

    void deleteSkuBySpuId(Long spuId);

    List<ProductSkuDTO> getSkuListBySpuId(Long spuId);

    Map<Long, List<ProductSkuDTO>> getSkuMapBySpuIds(Set<Long> spuIds);
}
