package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.followba.store.dao.convert.ProductSpuConvert;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductSpuDTO;
import com.followba.store.dao.mapper.ProductSpuMapper;
import com.followba.store.dao.po.ProductSpu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class BizProductSpuMapper {

    @Resource
    private ProductSpuMapper mapper;

    public void insert(ProductSpuDTO dto) {
        mapper.insert(ProductSpuConvert.INSTANCE.toPO(dto));
    }

    public void updateById(ProductSpuDTO dto) {
        mapper.updateById(ProductSpuConvert.INSTANCE.toPO(dto));
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    public ProductSpuDTO selectById(Long id) {
        return ProductSpuConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public List<ProductSpuDTO> selectBatchIds(Collection<Long> ids) {
        return ProductSpuConvert.INSTANCE.toDTO(mapper.selectBatchIds(ids));
    }

    public List<ProductSpuDTO> selectListByStatus(Integer status) {
        LambdaQueryWrapper<ProductSpu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpu::getStatus, status);
        wrapper.orderByDesc(ProductSpu::getSort).orderByDesc(ProductSpu::getId);
        return ProductSpuConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public PageDTO<ProductSpuDTO> selectPage(Integer pageNum, Integer pageSize, String name, Byte status, Long categoryId, Integer brandId) {
        Page<ProductSpu> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductSpu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null && !name.isBlank(), ProductSpu::getName, name);
        wrapper.eq(status != null, ProductSpu::getStatus, status);
        wrapper.eq(categoryId != null, ProductSpu::getCategoryId, categoryId);
        wrapper.eq(brandId != null, ProductSpu::getBrandId, brandId);
        wrapper.orderByDesc(ProductSpu::getId);
        Page<ProductSpu> result = mapper.selectPage(page, wrapper);
        return PageDTO.of(result.getTotal(), ProductSpuConvert.INSTANCE.toDTO(result.getRecords()));
    }

    public Long countByStatus(Integer status) {
        LambdaQueryWrapper<ProductSpu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpu::getStatus, status);
        return mapper.selectCount(wrapper);
    }

    public Long countByCategoryId(Long categoryId) {
        LambdaQueryWrapper<ProductSpu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpu::getCategoryId, categoryId);
        return mapper.selectCount(wrapper);
    }
}
