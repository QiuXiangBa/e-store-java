package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.dao.convert.ProductSpuConvert;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductSpuDTO;
import com.followba.store.dao.enums.ProductSpuTabTypeEnum;
import com.followba.store.dao.enums.ProductSpuStatusEnum;
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
        ProductSpu po = ProductSpuConvert.INSTANCE.toPO(dto);
        mapper.insert(po);
        dto.setId(po.getId());
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

    public PageDTO<ProductSpuDTO> selectPage(Integer pageNum, Integer pageSize, String name, Integer tabType, Long categoryId, Long brandId) {
        Page<ProductSpu> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductSpu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null && !name.isBlank(), ProductSpu::getName, name);
        wrapper.eq(categoryId != null, ProductSpu::getCategoryId, categoryId);
        wrapper.eq(brandId != null, ProductSpu::getBrandId, brandId);
        appendTabQuery(tabType, wrapper);
        wrapper.orderByDesc(ProductSpu::getId);
        Page<ProductSpu> result = mapper.selectPage(page, wrapper);
        return PageDTO.of(result.getTotal(), ProductSpuConvert.INSTANCE.toDTO(result.getRecords()));
    }

    public Long countByStatus(Integer status) {
        LambdaQueryWrapper<ProductSpu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpu::getStatus, status);
        return mapper.selectCount(wrapper);
    }

    public Long countByStock(Integer stock) {
        LambdaQueryWrapper<ProductSpu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpu::getStock, stock);
        wrapper.ne(ProductSpu::getStatus, ProductSpuStatusEnum.RECYCLE.getCode());
        return mapper.selectCount(wrapper);
    }

    public Long countAlertStock() {
        LambdaQueryWrapper<ProductSpu> wrapper = new LambdaQueryWrapper<>();
        wrapper.le(ProductSpu::getStock, ProductConstants.ALERT_STOCK);
        wrapper.ne(ProductSpu::getStatus, ProductSpuStatusEnum.RECYCLE.getCode());
        return mapper.selectCount(wrapper);
    }

    public Long countByCategoryId(Long categoryId) {
        LambdaQueryWrapper<ProductSpu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpu::getCategoryId, categoryId);
        return mapper.selectCount(wrapper);
    }

    private void appendTabQuery(Integer tabType, LambdaQueryWrapper<ProductSpu> wrapper) {
        // 默认 tab 兜底为出售中 / Default tab falls back to "for sale"
        if (tabType == null || ProductSpuTabTypeEnum.FOR_SALE.getCode() == tabType) {
            wrapper.eq(ProductSpu::getStatus, ProductSpuStatusEnum.ENABLE.getCode());
            return;
        }
        if (ProductSpuTabTypeEnum.IN_WAREHOUSE.getCode() == tabType) {
            wrapper.eq(ProductSpu::getStatus, ProductSpuStatusEnum.DISABLE.getCode());
            return;
        }
        if (ProductSpuTabTypeEnum.SOLD_OUT.getCode() == tabType) {
            wrapper.eq(ProductSpu::getStock, ProductConstants.DEFAULT_ZERO);
            wrapper.ne(ProductSpu::getStatus, ProductSpuStatusEnum.RECYCLE.getCode());
            return;
        }
        if (ProductSpuTabTypeEnum.ALERT_STOCK.getCode() == tabType) {
            wrapper.le(ProductSpu::getStock, ProductConstants.ALERT_STOCK);
            wrapper.ne(ProductSpu::getStatus, ProductSpuStatusEnum.RECYCLE.getCode());
            return;
        }
        if (ProductSpuTabTypeEnum.RECYCLE_BIN.getCode() == tabType) {
            wrapper.eq(ProductSpu::getStatus, ProductSpuStatusEnum.RECYCLE.getCode());
            return;
        }
        wrapper.eq(ProductSpu::getStatus, ProductSpuStatusEnum.ENABLE.getCode());
    }
}
