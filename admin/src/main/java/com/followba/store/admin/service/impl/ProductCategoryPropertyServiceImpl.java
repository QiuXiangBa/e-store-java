package com.followba.store.admin.service.impl;

import com.followba.store.admin.convert.ProductCategoryPropertyConvert;
import com.followba.store.admin.service.ProductCategoryPropertyService;
import com.followba.store.admin.service.ProductCategoryService;
import com.followba.store.admin.vo.in.ProductCategoryPropertySaveIn;
import com.followba.store.admin.vo.out.ProductCategoryPropertyRespVO;
import com.followba.store.common.exception.BizException;
import com.followba.store.dao.biz.BizProductCategoryPropertyMapper;
import com.followba.store.dao.biz.BizProductPropertyMapper;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.dto.ProductCategoryPropertyDTO;
import com.followba.store.dao.dto.ProductPropertyDTO;
import com.followba.store.dao.enums.ProductPropertyTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductCategoryPropertyServiceImpl implements ProductCategoryPropertyService {

    @Resource
    private ProductCategoryService productCategoryService;

    @Resource
    private BizProductCategoryPropertyMapper bizProductCategoryPropertyMapper;

    @Resource
    private BizProductPropertyMapper bizProductPropertyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(ProductCategoryPropertySaveIn reqVO) {
        productCategoryService.validateCategory(reqVO.getCategoryId());
        if (reqVO.getItems() == null || reqVO.getItems().isEmpty()) {
            throw new BizException(ProductConstants.CATEGORY_PROPERTY_BATCH_EMPTY);
        }
        if (reqVO.getItems().size() > ProductConstants.CATEGORY_PROPERTY_MAX_SIZE) {
            throw new BizException(ProductConstants.CATEGORY_PROPERTY_BATCH_SIZE_EXCEED);
        }

        Set<Long> propertyIds = new HashSet<>();
        for (ProductCategoryPropertySaveIn.Item item : reqVO.getItems()) {
            if (!propertyIds.add(item.getPropertyId())) {
                throw new BizException(ProductConstants.CATEGORY_PROPERTY_ID_DUPLICATE);
            }
        }
        List<ProductPropertyDTO> propertyList = bizProductPropertyMapper.selectByIds(propertyIds);
        if (propertyList.size() != propertyIds.size()) {
            throw new BizException(ProductConstants.PROPERTY_NOT_EXISTS);
        }
        Map<Long, ProductPropertyDTO> propertyMap = propertyList.stream()
                .collect(Collectors.toMap(ProductPropertyDTO::getId, Function.identity(), (left, right) -> left));
        for (ProductCategoryPropertySaveIn.Item item : reqVO.getItems()) {
            ProductPropertyDTO property = propertyMap.get(item.getPropertyId());
            if (property == null || property.getPropertyType() == null) {
                continue;
            }
            if (ProductPropertyTypeEnum.SALES.getCode() != property.getPropertyType()
                    && (Boolean.TRUE.equals(item.getSupportValueImage()) || Boolean.TRUE.equals(item.getValueImageRequired()))) {
                throw new BizException(ProductConstants.PROPERTY_TYPE_INVALID, "仅销售属性支持规格值配图设置");
            }
            if (Boolean.TRUE.equals(item.getValueImageRequired()) && !Boolean.TRUE.equals(item.getSupportValueImage())) {
                throw new BizException(ProductConstants.PROPERTY_TYPE_INVALID, "规格值图片必填依赖开启规格值配图");
            }
        }

        List<ProductCategoryPropertyDTO> dtoList = ProductCategoryPropertyConvert.INSTANCE.toDTO(reqVO.getItems());
        dtoList.forEach(item -> item.setCategoryId(reqVO.getCategoryId()));
        bizProductCategoryPropertyMapper.replaceByCategoryId(reqVO.getCategoryId(), dtoList);
    }

    @Override
    public List<ProductCategoryPropertyRespVO> listByCategoryId(Long categoryId, Integer propertyType) {
        productCategoryService.validateCategory(categoryId);
        List<ProductCategoryPropertyDTO> list = bizProductCategoryPropertyMapper.selectListByCategoryId(categoryId);
        if (list.isEmpty()) {
            return List.of();
        }
        Set<Long> propertyIds = list.stream().map(ProductCategoryPropertyDTO::getPropertyId).collect(Collectors.toSet());
        Map<Long, ProductPropertyDTO> propertyMap = bizProductPropertyMapper.selectByIds(propertyIds)
                .stream()
                .collect(Collectors.toMap(ProductPropertyDTO::getId, Function.identity(), (left, right) -> left));

        List<ProductCategoryPropertyRespVO> respList = ProductCategoryPropertyConvert.INSTANCE.toVO(list);
        respList.forEach(item -> {
            ProductPropertyDTO property = propertyMap.get(item.getPropertyId());
            if (property != null) {
                item.setPropertyName(property.getName());
                item.setPropertyType(property.getPropertyType());
            }
        });
        if (propertyType == null) {
            return respList;
        }
        return respList.stream()
                .filter(item -> propertyType.equals(item.getPropertyType()))
                .toList();
    }
}
