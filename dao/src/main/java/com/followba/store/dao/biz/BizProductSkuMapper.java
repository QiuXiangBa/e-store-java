package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.convert.ProductSkuConvert;
import com.followba.store.dao.dto.ProductSkuDTO;
import com.followba.store.dao.mapper.ProductSkuMapper;
import com.followba.store.dao.po.ProductSku;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class BizProductSkuMapper {

    @Resource
    private ProductSkuMapper mapper;

    public void insert(ProductSkuDTO dto) {
        mapper.insert(ProductSkuConvert.INSTANCE.toPO(dto));
    }

    public void insertBatch(List<ProductSkuDTO> list) {
        mapper.batchInsert(list.stream().map(ProductSkuConvert.INSTANCE::toPO).toList());
    }

    public ProductSkuDTO selectById(Long id) {
        return ProductSkuConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public void deleteBySpuId(Long spuId) {
        LambdaQueryWrapper<ProductSku> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSku::getSpuId, spuId);
        mapper.delete(wrapper);
    }

    public List<ProductSkuDTO> selectListBySpuId(Long spuId) {
        LambdaQueryWrapper<ProductSku> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSku::getSpuId, spuId);
        wrapper.orderByAsc(ProductSku::getId);
        return ProductSkuConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public List<ProductSkuDTO> selectListBySpuIds(Set<Long> spuIds) {
        if (spuIds == null || spuIds.isEmpty()) {
            return List.of();
        }
        LambdaQueryWrapper<ProductSku> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ProductSku::getSpuId, spuIds);
        wrapper.orderByAsc(ProductSku::getId);
        return ProductSkuConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }
}
