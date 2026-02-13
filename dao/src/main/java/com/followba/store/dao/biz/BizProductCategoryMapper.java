package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.followba.store.dao.convert.ProductCategoryConvert;
import com.followba.store.dao.dto.ProductCategoryDTO;
import com.followba.store.dao.mapper.ProductCategoryMapper;
import com.followba.store.dao.po.ProductCategory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BizProductCategoryMapper {

    @Resource
    private ProductCategoryMapper mapper;

    public void insert(ProductCategoryDTO dto) {
        mapper.insert(ProductCategoryConvert.INSTANCE.toPO(dto));
    }

    public void updateById(ProductCategoryDTO dto) {
        mapper.updateById(ProductCategoryConvert.INSTANCE.toPO(dto));
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    public ProductCategoryDTO selectById(Long id) {
        return ProductCategoryConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public List<ProductCategoryDTO> selectByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        Set<Long> idSet = ids.stream().collect(Collectors.toSet());
        LambdaQueryWrapper<ProductCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ProductCategory::getId, idSet);
        return ProductCategoryConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public Long countByParentId(Long parentId) {
        LambdaQueryWrapper<ProductCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductCategory::getParentId, parentId);
        return mapper.selectCount(wrapper);
    }

    public List<ProductCategoryDTO> selectList(String name, Byte status, Long parentId) {
        LambdaQueryWrapper<ProductCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null && !name.isBlank(), ProductCategory::getName, name);
        wrapper.eq(status != null, ProductCategory::getStatus, status);
        wrapper.eq(parentId != null, ProductCategory::getParentId, parentId);
        wrapper.orderByAsc(ProductCategory::getSort).orderByDesc(ProductCategory::getId);
        return ProductCategoryConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public void updateSortBatch(List<ProductCategoryDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return;
        }
        for (ProductCategoryDTO dto : dtoList) {
            LambdaUpdateWrapper<ProductCategory> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(ProductCategory::getId, dto.getId())
                    .set(ProductCategory::getSort, dto.getSort());
            mapper.update(null, wrapper);
        }
    }
}
