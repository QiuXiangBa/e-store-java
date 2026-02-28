package com.followba.store.product.service.impl;

import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.biz.BizProductSkuMapper;
import com.followba.store.dao.biz.BizProductSpuMapper;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductSkuDTO;
import com.followba.store.dao.dto.ProductSpuDTO;
import com.followba.store.dao.enums.ProductSpuStatusEnum;
import com.followba.store.product.dto.ProductAppSkuDTO;
import com.followba.store.product.dto.ProductAppSpuDTO;
import com.followba.store.product.dto.ProductAppSpuDetailDTO;
import com.followba.store.product.dto.ProductPageQueryDTO;
import com.followba.store.product.service.MallProductService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MallProductServiceImpl implements MallProductService {

    @Resource
    private BizProductSpuMapper bizProductSpuMapper;

    @Resource
    private BizProductSkuMapper bizProductSkuMapper;

    @Override
    public PageResp<ProductAppSpuDTO> page(ProductPageQueryDTO queryDTO) {
        Integer pageNum = queryDTO.getPageNum() == null ? ProductConstants.CART_DEFAULT_PAGE_NUM : queryDTO.getPageNum();
        Integer pageSize = queryDTO.getPageSize() == null ? ProductConstants.CART_DEFAULT_PAGE_SIZE : queryDTO.getPageSize();
        PageDTO<ProductSpuDTO> pageDTO = bizProductSpuMapper.selectPage(pageNum, pageSize, queryDTO.getKeyword(),
                null, queryDTO.getCategoryId(), null);
        List<ProductAppSpuDTO> list = pageDTO.getList().stream()
                .filter(spu -> Objects.equals(spu.getStatus(), ProductSpuStatusEnum.ENABLE.getCode()))
                .map(this::toProductAppSpuDTO)
                .toList();
        return PageResp.of(pageDTO.getTotal(), list);
    }

    @Override
    public ProductAppSpuDetailDTO detail(Long spuId) {
        ProductSpuDTO spuDTO = bizProductSpuMapper.selectById(spuId);
        if (spuDTO == null || !Objects.equals(spuDTO.getStatus(), ProductSpuStatusEnum.ENABLE.getCode())) {
            return null;
        }
        List<ProductSkuDTO> skuDTOList = bizProductSkuMapper.selectListBySpuId(spuId);
        ProductAppSpuDetailDTO detailDTO = new ProductAppSpuDetailDTO();
        detailDTO.setSpuId(spuDTO.getId());
        detailDTO.setName(spuDTO.getName());
        detailDTO.setIntroduction(spuDTO.getIntroduction());
        detailDTO.setPicUrl(spuDTO.getPicUrl());
        detailDTO.setSliderPicUrls(spuDTO.getSliderPicUrls());
        List<ProductAppSkuDTO> skuList = new ArrayList<>();
        for (ProductSkuDTO skuDTO : skuDTOList) {
            if (skuDTO.getStock() == null || skuDTO.getStock() <= ProductConstants.DEFAULT_ZERO) {
                continue;
            }
            skuList.add(toProductAppSkuDTO(skuDTO));
        }
        detailDTO.setSkuList(skuList);
        return detailDTO;
    }

    private ProductAppSpuDTO toProductAppSpuDTO(ProductSpuDTO spuDTO) {
        ProductAppSpuDTO dto = new ProductAppSpuDTO();
        dto.setSpuId(spuDTO.getId());
        dto.setName(spuDTO.getName());
        dto.setPicUrl(spuDTO.getPicUrl());
        dto.setPrice(spuDTO.getPrice());
        dto.setMarketPrice(spuDTO.getMarketPrice());
        dto.setSalesCount(spuDTO.getSalesCount());
        return dto;
    }

    private ProductAppSkuDTO toProductAppSkuDTO(ProductSkuDTO skuDTO) {
        ProductAppSkuDTO dto = new ProductAppSkuDTO();
        dto.setSkuId(skuDTO.getId());
        dto.setPrice(skuDTO.getPrice());
        dto.setMarketPrice(skuDTO.getMarketPrice());
        dto.setStock(skuDTO.getStock());
        dto.setPicUrl(skuDTO.getPicUrl());
        dto.setProperties(buildPropertyTexts(skuDTO));
        return dto;
    }

    private List<String> buildPropertyTexts(ProductSkuDTO skuDTO) {
        if (skuDTO.getProperties() == null || skuDTO.getProperties().isEmpty()) {
            return List.of();
        }
        return skuDTO.getProperties().stream()
                .map(ProductSkuDTO.Property::getValueName)
                .filter(Objects::nonNull)
                .toList();
    }
}
