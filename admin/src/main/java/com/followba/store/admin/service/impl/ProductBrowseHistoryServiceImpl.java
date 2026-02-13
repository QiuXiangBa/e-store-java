package com.followba.store.admin.service.impl;

import com.followba.store.admin.convert.ProductBrowseHistoryConvert;
import com.followba.store.admin.service.ProductBrowseHistoryService;
import com.followba.store.admin.vo.in.ProductBrowseHistoryPageIn;
import com.followba.store.admin.vo.out.ProductBrowseHistoryRespVO;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.biz.BizProductBrowseHistoryMapper;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductBrowseHistoryDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ProductBrowseHistoryServiceImpl implements ProductBrowseHistoryService {

    @Resource
    private BizProductBrowseHistoryMapper bizProductBrowseHistoryMapper;

    @Override
    public PageResp<ProductBrowseHistoryRespVO> getBrowseHistoryPage(ProductBrowseHistoryPageIn reqVO) {
        PageDTO<ProductBrowseHistoryDTO> page = bizProductBrowseHistoryMapper.selectPage(reqVO.getPageNum(), reqVO.getPageSize(),
                reqVO.getUserId(), reqVO.getSpuId(), reqVO.getUserDeleted());
        return ProductBrowseHistoryConvert.INSTANCE.toVO(page);
    }
}
