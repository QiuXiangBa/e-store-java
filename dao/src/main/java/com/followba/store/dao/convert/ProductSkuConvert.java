package com.followba.store.dao.convert;

import com.followba.store.dao.dto.ProductSkuDTO;
import com.followba.store.dao.po.ProductSku;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ProductSkuConvert {
    ProductSkuConvert INSTANCE = new ProductSkuConvert() {
    };

    /**
     * PO 转 DTO（兼容 properties 历史 LinkedHashMap 数据）/ PO to DTO with legacy LinkedHashMap compatibility.
     */
    default ProductSkuDTO toDTO(ProductSku po) {
        if (po == null) {
            return null;
        }
        ProductSkuDTO dto = new ProductSkuDTO();
        dto.setId(po.getId());
        dto.setSpuId(po.getSpuId());
        dto.setProperties(toPropertyDTOList(po.getProperties()));
        dto.setPrice(po.getPrice());
        dto.setMarketPrice(po.getMarketPrice());
        dto.setCostPrice(po.getCostPrice());
        dto.setBarCode(po.getBarCode());
        dto.setPicUrl(po.getPicUrl());
        dto.setStock(po.getStock());
        dto.setWeight(po.getWeight());
        dto.setVolume(po.getVolume());
        dto.setSubCommissionFirstPrice(po.getSubCommissionFirstPrice());
        dto.setSubCommissionSecondPrice(po.getSubCommissionSecondPrice());
        dto.setSalesCount(po.getSalesCount());
        dto.setCreator(po.getCreator());
        dto.setCreateTime(po.getCreateTime());
        dto.setUpdater(po.getUpdater());
        dto.setUpdateTime(po.getUpdateTime());
        dto.setDeleted(po.getDeleted());
        dto.setTenantId(po.getTenantId());
        return dto;
    }

    default List<ProductSkuDTO> toDTO(List<ProductSku> poList) {
        if (poList == null || poList.isEmpty()) {
            return List.of();
        }
        List<ProductSkuDTO> dtoList = new ArrayList<>(poList.size());
        for (ProductSku po : poList) {
            dtoList.add(toDTO(po));
        }
        return dtoList;
    }

    default ProductSku toPO(ProductSkuDTO dto) {
        if (dto == null) {
            return null;
        }
        ProductSku po = new ProductSku();
        po.setId(dto.getId());
        po.setSpuId(dto.getSpuId());
        po.setProperties(toPropertyPOList(dto.getProperties()));
        po.setPrice(dto.getPrice());
        po.setMarketPrice(dto.getMarketPrice());
        po.setCostPrice(dto.getCostPrice());
        po.setBarCode(dto.getBarCode());
        po.setPicUrl(dto.getPicUrl());
        po.setStock(dto.getStock());
        po.setWeight(dto.getWeight());
        po.setVolume(dto.getVolume());
        po.setSubCommissionFirstPrice(dto.getSubCommissionFirstPrice());
        po.setSubCommissionSecondPrice(dto.getSubCommissionSecondPrice());
        po.setSalesCount(dto.getSalesCount());
        po.setCreator(dto.getCreator());
        po.setCreateTime(dto.getCreateTime());
        po.setUpdater(dto.getUpdater());
        po.setUpdateTime(dto.getUpdateTime());
        po.setDeleted(dto.getDeleted());
        po.setTenantId(dto.getTenantId());
        return po;
    }

    /**
     * 将属性列表转换为 DTO，容忍运行时元素为 Map。/ Convert property list to DTO and tolerate runtime Map elements.
     */
    default List<ProductSkuDTO.Property> toPropertyDTOList(List<ProductSku.Property> propertyList) {
        if (propertyList == null || propertyList.isEmpty()) {
            return List.of();
        }
        List<ProductSkuDTO.Property> result = new ArrayList<>(propertyList.size());
        for (Object item : propertyList) {
            ProductSkuDTO.Property property = toPropertyDTO(item);
            if (property != null) {
                result.add(property);
            }
        }
        return result;
    }

    default ProductSkuDTO.Property toPropertyDTO(Object source) {
        if (source == null) {
            return null;
        }
        ProductSkuDTO.Property target = new ProductSkuDTO.Property();
        if (source instanceof ProductSku.Property property) {
            target.setPropertyId(property.getPropertyId());
            target.setPropertyName(property.getPropertyName());
            target.setValueId(property.getValueId());
            target.setValueName(property.getValueName());
            target.setValuePicUrl(property.getValuePicUrl());
            return target;
        }
        if (source instanceof Map<?, ?> map) {
            target.setPropertyId(asLong(map.get("propertyId")));
            target.setPropertyName(asString(map.get("propertyName")));
            target.setValueId(asLong(map.get("valueId")));
            target.setValueName(asString(map.get("valueName")));
            target.setValuePicUrl(asString(map.get("valuePicUrl")));
            return target;
        }
        return null;
    }

    default List<ProductSku.Property> toPropertyPOList(List<ProductSkuDTO.Property> propertyList) {
        if (propertyList == null || propertyList.isEmpty()) {
            return List.of();
        }
        List<ProductSku.Property> result = new ArrayList<>(propertyList.size());
        for (ProductSkuDTO.Property source : propertyList) {
            ProductSku.Property target = new ProductSku.Property();
            target.setPropertyId(source.getPropertyId());
            target.setPropertyName(source.getPropertyName());
            target.setValueId(source.getValueId());
            target.setValueName(source.getValueName());
            target.setValuePicUrl(source.getValuePicUrl());
            result.add(target);
        }
        return result;
    }

    default Long asLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        try {
            return Long.parseLong(String.valueOf(value));
        } catch (NumberFormatException ignore) {
            return null;
        }
    }

    default String asString(Object value) {
        return value == null ? null : String.valueOf(value);
    }
}
