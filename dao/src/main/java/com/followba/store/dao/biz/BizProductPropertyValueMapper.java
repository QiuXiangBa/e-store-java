package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.convert.ProductPropertyValueConvert;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductPropertyValueDTO;
import com.followba.store.dao.mapper.ProductPropertyValueMapper;
import com.followba.store.dao.po.ProductPropertyValue;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class BizProductPropertyValueMapper {

    @Resource
    private ProductPropertyValueMapper mapper;

    public void insert(ProductPropertyValueDTO dto) {
        mapper.insert(ProductPropertyValueConvert.INSTANCE.toPO(dto));
    }

    public void updateById(ProductPropertyValueDTO dto) {
        mapper.updateById(ProductPropertyValueConvert.INSTANCE.toPO(dto));
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    public ProductPropertyValueDTO selectById(Long id) {
        return ProductPropertyValueConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public Long countByPropertyId(Long propertyId) {
        LambdaQueryWrapper<ProductPropertyValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductPropertyValue::getPropertyId, propertyId);
        return mapper.selectCount(wrapper);
    }

    public ProductPropertyValueDTO selectByPropertyIdAndName(Long propertyId, String name) {
        LambdaQueryWrapper<ProductPropertyValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductPropertyValue::getPropertyId, propertyId);
        wrapper.eq(ProductPropertyValue::getName, name);
        return ProductPropertyValueConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public List<ProductPropertyValueDTO> selectListByPropertyIds(Collection<Long> propertyIds) {
        if (propertyIds == null || propertyIds.isEmpty()) {
            return List.of();
        }
        LambdaQueryWrapper<ProductPropertyValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ProductPropertyValue::getPropertyId, propertyIds);
        return ProductPropertyValueConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public List<ProductPropertyValueDTO> selectByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        LambdaQueryWrapper<ProductPropertyValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ProductPropertyValue::getId, ids);
        return ProductPropertyValueConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public PageDTO<ProductPropertyValueDTO> selectPage(Integer pageNum, Integer pageSize, Long propertyId, String name, Byte status) {
        Page<ProductPropertyValue> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductPropertyValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(propertyId != null, ProductPropertyValue::getPropertyId, propertyId);
        wrapper.like(name != null && !name.isBlank(), ProductPropertyValue::getName, name);
        wrapper.eq(status != null, ProductPropertyValue::getStatus, status);
        wrapper.orderByAsc(ProductPropertyValue::getId);
        Page<ProductPropertyValue> result = mapper.selectPage(page, wrapper);
        return PageDTO.of(result.getTotal(), ProductPropertyValueConvert.INSTANCE.toDTO(result.getRecords()));
    }

    public List<ProductPropertyValueDTO> selectSimpleList(Long propertyId) {
        LambdaQueryWrapper<ProductPropertyValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductPropertyValue::getPropertyId, propertyId);
        wrapper.eq(ProductPropertyValue::getStatus, ProductConstants.PROPERTY_VALUE_SIMPLE_ENABLED_STATUS);
        wrapper.orderByAsc(ProductPropertyValue::getId);
        return ProductPropertyValueConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }
}
