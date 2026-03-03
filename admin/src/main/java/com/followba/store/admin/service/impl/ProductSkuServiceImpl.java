package com.followba.store.admin.service.impl;

import com.followba.store.admin.convert.ProductSkuConvert;
import com.followba.store.admin.service.ProductSkuService;
import com.followba.store.admin.vo.in.ProductSkuSaveIn;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizProductPropertyMapper;
import com.followba.store.dao.biz.BizProductPropertyValueMapper;
import com.followba.store.dao.biz.BizProductSkuMapper;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.dto.ProductPropertyValueDTO;
import com.followba.store.dao.dto.ProductSkuDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductSkuServiceImpl implements ProductSkuService {

    @Resource
    private BizProductSkuMapper bizProductSkuMapper;

    @Resource
    private BizProductPropertyMapper bizProductPropertyMapper;

    @Resource
    private BizProductPropertyValueMapper bizProductPropertyValueMapper;

    @Override
    public void validateSkuList(List<ProductSkuSaveIn> skus, Boolean specType) {
        if (skus == null || skus.isEmpty()) {
            throw new BizException(ProductConstants.SKU_NOT_EXISTS);
        }

        if (Boolean.FALSE.equals(specType)) {
            if (skus.size() != ProductConstants.SINGLE_SKU_SIZE) {
                throw new BizException(ProductConstants.SKU_PROPERTIES_DUPLICATED, "单规格商品仅允许一个 SKU");
            }
            ProductSkuSaveIn sku = skus.get(ProductConstants.DEFAULT_ZERO);
            ProductSkuSaveIn.Property defaultProperty = new ProductSkuSaveIn.Property();
            defaultProperty.setPropertyId(ProductConstants.SKU_DEFAULT_PROPERTY_ID);
            defaultProperty.setPropertyName(ProductConstants.SKU_DEFAULT_PROPERTY_NAME);
            defaultProperty.setValueId(ProductConstants.SKU_DEFAULT_PROPERTY_VALUE_ID);
            defaultProperty.setValueName(ProductConstants.SKU_DEFAULT_PROPERTY_VALUE_NAME);
            sku.setProperties(List.of(defaultProperty));
            return;
        }

        Set<Long> propertyIds = skus.stream()
                .filter(sku -> sku.getProperties() != null)
                .flatMap(sku -> sku.getProperties().stream())
                .map(ProductSkuSaveIn.Property::getPropertyId)
                .filter(id -> id != null)
                .collect(Collectors.toSet());
        if (propertyIds.isEmpty()) {
            throw new BizException(ProductConstants.SKU_PROPERTIES_NOT_EXISTS);
        }
        if (bizProductPropertyMapper.selectByIds(propertyIds).size() != propertyIds.size()) {
            throw new BizException(ProductConstants.SKU_PROPERTIES_NOT_EXISTS);
        }

        Map<Long, ProductPropertyValueDTO> propertyValueMap = bizProductPropertyValueMapper.selectListByPropertyIds(propertyIds)
                .stream()
                .collect(Collectors.toMap(ProductPropertyValueDTO::getId, value -> value, (left, right) -> left));

        for (ProductSkuSaveIn sku : skus) {
            if (sku.getProperties() == null || sku.getProperties().isEmpty()) {
                throw new BizException(ProductConstants.SKU_PROPERTIES_NOT_EXISTS);
            }
            Set<Long> skuPropertyIds = new HashSet<>();
            for (ProductSkuSaveIn.Property property : sku.getProperties()) {
                ProductPropertyValueDTO propertyValue = propertyValueMap.get(property.getValueId());
                if (propertyValue == null || propertyValue.getPropertyId() == null) {
                    throw new BizException(ProductConstants.SKU_PROPERTIES_NOT_EXISTS);
                }
                skuPropertyIds.add(propertyValue.getPropertyId());
            }
            if (skuPropertyIds.size() != sku.getProperties().size()) {
                throw new BizException(ProductConstants.SKU_PROPERTIES_DUPLICATED);
            }
        }

        int firstAttrSize = skus.get(ProductConstants.DEFAULT_ZERO).getProperties().size();
        for (int index = ProductConstants.DEFAULT_ONE; index < skus.size(); index++) {
            if (skus.get(index).getProperties().size() != firstAttrSize) {
                throw new BizException(ProductConstants.SKU_ATTR_NUMBERS_MUST_BE_EQUALS);
            }
        }

        Set<String> skuKeys = new HashSet<>();
        for (ProductSkuSaveIn sku : skus) {
            if (!skuKeys.add(buildPropertyKeyFromSaveIn(sku.getProperties()))) {
                throw new BizException(ProductConstants.SKU_DUPLICATED);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSkuList(Long spuId, List<ProductSkuSaveIn> skus) {
        List<ProductSkuDTO> skuDTOList = ProductSkuConvert.INSTANCE.toDTO(skus);
        skuDTOList.forEach(dto -> {
            dto.setSpuId(spuId);
            dto.setSalesCount(ProductConstants.DEFAULT_ZERO);
        });
        bizProductSkuMapper.insertBatch(skuDTOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSkuList(Long spuId, List<ProductSkuSaveIn> skus) {
        List<ProductSkuDTO> existsSkus = bizProductSkuMapper.selectListBySpuId(spuId);
        Map<String, ProductSkuDTO> existsSkuMap = existsSkus.stream().collect(Collectors.toMap(
                sku -> buildPropertyKeyFromDTO(sku.getProperties()),
                sku -> sku,
                (left, right) -> left,
                HashMap::new
        ));

        List<ProductSkuDTO> insertSkus = new ArrayList<>();
        List<ProductSkuDTO> updateSkus = new ArrayList<>();

        List<ProductSkuDTO> incomingSkus = ProductSkuConvert.INSTANCE.toDTO(skus);
        for (ProductSkuDTO sku : incomingSkus) {
            sku.setSpuId(spuId);
            String propertyKey = buildPropertyKeyFromDTO(sku.getProperties());
            ProductSkuDTO existsSku = existsSkuMap.remove(propertyKey);
            if (existsSku != null) {
                sku.setId(existsSku.getId());
                sku.setSalesCount(existsSku.getSalesCount());
                updateSkus.add(sku);
            } else {
                sku.setSalesCount(ProductConstants.DEFAULT_ZERO);
                insertSkus.add(sku);
            }
        }

        if (!insertSkus.isEmpty()) {
            bizProductSkuMapper.insertBatch(insertSkus);
        }
        if (!updateSkus.isEmpty()) {
            bizProductSkuMapper.updateBatch(updateSkus);
        }
        if (!existsSkuMap.isEmpty()) {
            bizProductSkuMapper.deleteByIds(existsSkuMap.values().stream().map(ProductSkuDTO::getId).collect(Collectors.toSet()));
        }
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSkuProperty(Long propertyId, String propertyName) {
        List<ProductSkuDTO> skuList = bizProductSkuMapper.selectList();
        if (skuList.isEmpty()) {
            return ProductConstants.DEFAULT_ZERO;
        }
        Map<Long, ProductSkuDTO> updatedSkuMap = new LinkedHashMap<>();
        for (ProductSkuDTO sku : skuList) {
            if (sku.getProperties() == null || sku.getProperties().isEmpty()) {
                continue;
            }
            boolean changed = false;
            for (ProductSkuDTO.Property property : sku.getProperties()) {
                if (propertyId.equals(property.getPropertyId())) {
                    property.setPropertyName(propertyName);
                    changed = true;
                }
            }
            if (changed && sku.getId() != null) {
                updatedSkuMap.put(sku.getId(), sku);
            }
        }
        if (updatedSkuMap.isEmpty()) {
            return ProductConstants.DEFAULT_ZERO;
        }
        bizProductSkuMapper.updateBatch(new ArrayList<>(updatedSkuMap.values()));
        return updatedSkuMap.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSkuPropertyValue(Long propertyValueId, String propertyValueName) {
        List<ProductSkuDTO> skuList = bizProductSkuMapper.selectList();
        if (skuList.isEmpty()) {
            return ProductConstants.DEFAULT_ZERO;
        }
        Map<Long, ProductSkuDTO> updatedSkuMap = new LinkedHashMap<>();
        for (ProductSkuDTO sku : skuList) {
            if (sku.getProperties() == null || sku.getProperties().isEmpty()) {
                continue;
            }
            boolean changed = false;
            for (ProductSkuDTO.Property property : sku.getProperties()) {
                if (propertyValueId.equals(property.getValueId())) {
                    property.setValueName(propertyValueName);
                    changed = true;
                }
            }
            if (changed && sku.getId() != null) {
                updatedSkuMap.put(sku.getId(), sku);
            }
        }
        if (updatedSkuMap.isEmpty()) {
            return ProductConstants.DEFAULT_ZERO;
        }
        bizProductSkuMapper.updateBatch(new ArrayList<>(updatedSkuMap.values()));
        return updatedSkuMap.size();
    }

    private String buildPropertyKeyFromSaveIn(List<ProductSkuSaveIn.Property> properties) {
        if (properties == null || properties.isEmpty()) {
            return "";
        }
        return buildPropertyValueKey(properties.stream().map(ProductSkuSaveIn.Property::getValueId).toList());
    }

    private String buildPropertyKeyFromDTO(List<ProductSkuDTO.Property> properties) {
        if (properties == null || properties.isEmpty()) {
            return "";
        }
        return buildPropertyValueKey(properties.stream().map(ProductSkuDTO.Property::getValueId).toList());
    }

    private String buildPropertyValueKey(Collection<Long> valueIds) {
        return valueIds.stream()
                .filter(id -> id != null)
                .sorted(Comparator.naturalOrder())
                .map(String::valueOf)
                .collect(Collectors.joining("_"));
    }
}
