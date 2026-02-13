package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.followba.store.dao.convert.ProductFavoriteConvert;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductFavoriteDTO;
import com.followba.store.dao.mapper.ProductFavoriteMapper;
import com.followba.store.dao.po.ProductFavorite;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BizProductFavoriteMapper {

    @Resource
    private ProductFavoriteMapper mapper;

    public PageDTO<ProductFavoriteDTO> selectPage(Integer pageNum, Integer pageSize, Long userId, Long spuId) {
        Page<ProductFavorite> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId != null, ProductFavorite::getUserId, userId);
        wrapper.eq(spuId != null, ProductFavorite::getSpuId, spuId);
        wrapper.orderByDesc(ProductFavorite::getId);
        Page<ProductFavorite> result = mapper.selectPage(page, wrapper);
        return PageDTO.of(result.getTotal(), ProductFavoriteConvert.INSTANCE.toDTO(result.getRecords()));
    }
}
