package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.followba.store.dao.convert.ProductCommentConvert;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductCommentDTO;
import com.followba.store.dao.mapper.ProductCommentMapper;
import com.followba.store.dao.po.ProductComment;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BizProductCommentMapper {

    @Resource
    private ProductCommentMapper mapper;

    public PageDTO<ProductCommentDTO> selectPage(Integer pageNum, Integer pageSize, Long spuId, Long userId, Boolean visible) {
        Page<ProductComment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(spuId != null, ProductComment::getSpuId, spuId);
        wrapper.eq(userId != null, ProductComment::getUserId, userId);
        wrapper.eq(visible != null, ProductComment::getVisible, visible);
        wrapper.orderByDesc(ProductComment::getId);
        Page<ProductComment> result = mapper.selectPage(page, wrapper);
        return PageDTO.of(result.getTotal(), ProductCommentConvert.INSTANCE.toDTO(result.getRecords()));
    }

    public ProductCommentDTO selectById(Long id) {
        return ProductCommentConvert.INSTANCE.toDTO(mapper.selectById(id));
    }

    public void updateById(ProductCommentDTO dto) {
        mapper.updateById(ProductCommentConvert.INSTANCE.toPO(dto));
    }

    public void insert(ProductCommentDTO dto) {
        mapper.insert(ProductCommentConvert.INSTANCE.toPO(dto));
    }
}
