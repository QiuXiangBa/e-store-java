package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.convert.ProductPropertyConvert;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductPropertyDTO;
import com.followba.store.dao.mapper.ProductPropertyMapper;
import com.followba.store.dao.po.ProductProperty;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class BizProductPropertyMapper {

    @Resource
    private ProductPropertyMapper mapper;

    public void insert(ProductPropertyDTO dto) {
        mapper.insert(ProductPropertyConvert.INSTANCE.toPO(dto));
    }

    public void updateById(ProductPropertyDTO dto) {
        mapper.updateById(ProductPropertyConvert.INSTANCE.toPO(dto));
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    public ProductPropertyDTO selectById(Long id) {
        return ProductPropertyConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public ProductPropertyDTO selectByName(String name) {
        LambdaQueryWrapper<ProductProperty> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductProperty::getName, name);
        return ProductPropertyConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public List<ProductPropertyDTO> selectByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return ProductPropertyConvert.INSTANCE.toDTO(mapper.selectBatchIds(ids));
    }

    public PageDTO<ProductPropertyDTO> selectPage(Integer pageNum, Integer pageSize, String name, Byte status) {
        Page<ProductProperty> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductProperty> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null && !name.isBlank(), ProductProperty::getName, name);
        wrapper.eq(status != null, ProductProperty::getStatus, status);
        wrapper.orderByAsc(ProductProperty::getId);
        Page<ProductProperty> result = mapper.selectPage(page, wrapper);
        return PageDTO.of(result.getTotal(), ProductPropertyConvert.INSTANCE.toDTO(result.getRecords()));
    }

    public List<ProductPropertyDTO> selectSimpleList() {
        LambdaQueryWrapper<ProductProperty> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductProperty::getStatus, ProductConstants.PROPERTY_SIMPLE_ENABLED_STATUS);
        wrapper.orderByAsc(ProductProperty::getId);
        return ProductPropertyConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }
}
