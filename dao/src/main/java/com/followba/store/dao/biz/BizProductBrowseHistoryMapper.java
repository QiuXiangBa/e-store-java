package com.followba.store.dao.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.followba.store.dao.convert.ProductBrowseHistoryConvert;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductBrowseHistoryDTO;
import com.followba.store.dao.mapper.ProductBrowseHistoryMapper;
import com.followba.store.dao.po.ProductBrowseHistory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BizProductBrowseHistoryMapper {

    @Resource
    private ProductBrowseHistoryMapper mapper;

    public PageDTO<ProductBrowseHistoryDTO> selectPage(Integer pageNum, Integer pageSize, Long userId, Long spuId, Boolean userDeleted) {
        Page<ProductBrowseHistory> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ProductBrowseHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId != null, ProductBrowseHistory::getUserId, userId);
        wrapper.eq(spuId != null, ProductBrowseHistory::getSpuId, spuId);
        wrapper.eq(userDeleted != null, ProductBrowseHistory::getUserDeleted, userDeleted);
        wrapper.orderByDesc(ProductBrowseHistory::getId);
        Page<ProductBrowseHistory> result = mapper.selectPage(page, wrapper);
        return PageDTO.of(result.getTotal(), ProductBrowseHistoryConvert.INSTANCE.toDTO(result.getRecords()));
    }
}
