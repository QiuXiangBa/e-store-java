package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.followba.store.dao.convert.ProductBrandConvert;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductBrandDTO;
import com.followba.store.dao.mapper.ProductBrandMapper;
import com.followba.store.dao.po.ProductBrand;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BizProductBrandMapper {

    @Resource
    private ProductBrandMapper mapper;

    public void insert(ProductBrandDTO dto) {
        ProductBrand po = ProductBrandConvert.INSTANCE.toPO(dto);
        mapper.insert(po);
    }

    public void updateById(ProductBrandDTO dto) {
        ProductBrand po = ProductBrandConvert.INSTANCE.toPO(dto);
        mapper.updateById(po);
    }

    public void deleteById(Long id) { mapper.deleteById(id); }

    public ProductBrandDTO selectById(Long id) { return ProductBrandConvert.INSTANCE.toDTO(mapper.selectById(id)); }

    public ProductBrandDTO selectByName(String name) {
        LambdaQueryWrapper<ProductBrand> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductBrand::getName, name);
        return ProductBrandConvert.INSTANCE.toDTO(mapper.selectOne(wrapper));
    }

    public List<ProductBrandDTO> selectList(String name, Byte status) {
        LambdaQueryWrapper<ProductBrand> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null && !name.isBlank(), ProductBrand::getName, name);
        wrapper.eq(status != null, ProductBrand::getStatus, status);
        wrapper.orderByAsc(ProductBrand::getSort).orderByDesc(ProductBrand::getId);
        return ProductBrandConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public List<ProductBrandDTO> selectListByStatus(Integer status) {
        LambdaQueryWrapper<ProductBrand> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductBrand::getStatus, status);
        wrapper.orderByAsc(ProductBrand::getSort).orderByDesc(ProductBrand::getId);
        return ProductBrandConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public PageDTO<ProductBrandDTO> selectPage(Integer pageNum, Integer pageSize, String name, Byte status) {
        Page<ProductBrand> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductBrand> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null && !name.isBlank(), ProductBrand::getName, name);
        wrapper.eq(status != null, ProductBrand::getStatus, status);
        wrapper.orderByAsc(ProductBrand::getSort).orderByDesc(ProductBrand::getId);
        Page<ProductBrand> ipage = mapper.selectPage(page, wrapper);
        return PageDTO.of(ipage.getTotal(), ProductBrandConvert.INSTANCE.toDTO(ipage.getRecords())) ;
    }
}
