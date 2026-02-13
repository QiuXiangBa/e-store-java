package com.followba.store.admin.service.impl;

import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.admin.convert.ProductSkuConvert;
import com.followba.store.admin.service.ProductSkuService;
import com.followba.store.admin.vo.in.ProductSkuSaveIn;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizProductSkuMapper;
import com.followba.store.dao.dto.ProductSkuDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductSkuServiceImpl implements ProductSkuService {

    @Resource
    private BizProductSkuMapper bizProductSkuMapper;

    @Override
    public void validateSkuList(List<ProductSkuSaveIn> skus, Boolean specType) {
        if (skus == null || skus.isEmpty()) {
            throw new BizException(ProductConstants.SKU_NOT_EXISTS);
        }
        Set<String> skuKeys = new HashSet<>();
        for (ProductSkuSaveIn sku : skus) {
            String key = sku.getProperties() == null ? "" : sku.getProperties();
            if (!skuKeys.add(key)) {
                throw new BizException(ProductConstants.SKU_DUPLICATED);
            }
        }
        if (Boolean.FALSE.equals(specType) && skus.size() != ProductConstants.SINGLE_SKU_SIZE) {
            throw new BizException(ProductConstants.SKU_PROPERTIES_DUPLICATED, "单规格商品仅允许一个 SKU");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSkuList(Long spuId, List<ProductSkuSaveIn> skus) {
        List<ProductSkuDTO> skuDTOList = ProductSkuConvert.INSTANCE.toDTO(skus);
        for (ProductSkuDTO dto : skuDTOList) {
            dto.setSpuId(spuId);
            dto.setSalesCount(ProductConstants.DEFAULT_ZERO);
        }
        bizProductSkuMapper.insertBatch(skuDTOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSkuList(Long spuId, List<ProductSkuSaveIn> skus) {
        deleteSkuBySpuId(spuId);
        createSkuList(spuId, skus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSkuBySpuId(Long spuId) {
        bizProductSkuMapper.deleteBySpuId(spuId);
    }

    @Override
    public List<ProductSkuDTO> getSkuListBySpuId(Long spuId) {
        return bizProductSkuMapper.selectListBySpuId(spuId);
    }

    @Override
    public Map<Long, List<ProductSkuDTO>> getSkuMapBySpuIds(Set<Long> spuIds) {
        List<ProductSkuDTO> list = bizProductSkuMapper.selectListBySpuIds(spuIds);
        if (list.isEmpty()) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.groupingBy(ProductSkuDTO::getSpuId));
    }
}
