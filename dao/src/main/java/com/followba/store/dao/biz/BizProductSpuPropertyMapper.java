package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.followba.store.dao.convert.ProductSpuPropertyConvert;
import com.followba.store.dao.dto.ProductSpuPropertyDTO;
import com.followba.store.dao.mapper.ProductSpuPropertyMapper;
import com.followba.store.dao.po.ProductSpuProperty;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class BizProductSpuPropertyMapper {

    @Resource
    private ProductSpuPropertyMapper mapper;

    public List<ProductSpuPropertyDTO> selectListBySpuId(Long spuId) {
        LambdaQueryWrapper<ProductSpuProperty> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpuProperty::getSpuId, spuId);
        wrapper.orderByAsc(ProductSpuProperty::getSort).orderByAsc(ProductSpuProperty::getId);
        return ProductSpuPropertyConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public List<ProductSpuPropertyDTO> selectListBySpuIds(Collection<Long> spuIds) {
        if (spuIds == null || spuIds.isEmpty()) {
            return List.of();
        }
        LambdaQueryWrapper<ProductSpuProperty> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ProductSpuProperty::getSpuId, spuIds);
        wrapper.orderByAsc(ProductSpuProperty::getSort).orderByAsc(ProductSpuProperty::getId);
        return ProductSpuPropertyConvert.INSTANCE.toDTO(mapper.selectList(wrapper));
    }

    public void replaceBySpuId(Long spuId, List<ProductSpuPropertyDTO> list) {
        LambdaQueryWrapper<ProductSpuProperty> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpuProperty::getSpuId, spuId);
        mapper.delete(wrapper);
        if (list == null || list.isEmpty()) {
            return;
        }
        mapper.insertBatch(ProductSpuPropertyConvert.INSTANCE.toPO(list));
    }

    public void deleteBySpuId(Long spuId) {
        LambdaQueryWrapper<ProductSpuProperty> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpuProperty::getSpuId, spuId);
        mapper.delete(wrapper);
    }
}
