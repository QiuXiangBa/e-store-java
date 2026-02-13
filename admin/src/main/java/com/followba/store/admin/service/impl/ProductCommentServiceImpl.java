package com.followba.store.admin.service.impl;

import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.admin.convert.ProductCommentConvert;
import com.followba.store.admin.service.ProductCommentService;
import com.followba.store.admin.vo.in.ProductCommentCreateIn;
import com.followba.store.admin.vo.in.ProductCommentPageIn;
import com.followba.store.admin.vo.in.ProductCommentReplyIn;
import com.followba.store.admin.vo.in.ProductCommentUpdateVisibleIn;
import com.followba.store.admin.vo.out.ProductCommentRespVO;
import com.followba.store.common.exception.BizException;
import com.followba.store.common.resp.PageResp;
import com.followba.store.dao.biz.BizProductCommentMapper;
import com.followba.store.dao.biz.BizProductSkuMapper;
import com.followba.store.dao.biz.BizProductSpuMapper;
import com.followba.store.dao.dto.PageDTO;
import com.followba.store.dao.dto.ProductCommentDTO;
import com.followba.store.dao.dto.ProductSkuDTO;
import com.followba.store.dao.dto.ProductSpuDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    @Resource
    private BizProductCommentMapper bizProductCommentMapper;

    @Resource
    private BizProductSkuMapper bizProductSkuMapper;

    @Resource
    private BizProductSpuMapper bizProductSpuMapper;

    @Override
    public PageResp<ProductCommentRespVO> getCommentPage(ProductCommentPageIn reqVO) {
        PageDTO<ProductCommentDTO> page = bizProductCommentMapper.selectPage(
                reqVO.getPageNum(), reqVO.getPageSize(), reqVO.getSpuId(), reqVO.getUserId(), reqVO.getVisible()
        );
        return ProductCommentConvert.INSTANCE.toVO(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCommentVisible(ProductCommentUpdateVisibleIn reqVO) {
        ProductCommentDTO exists = validateCommentExists(reqVO.getId());
        ProductCommentDTO update = new ProductCommentDTO();
        update.setId(exists.getId());
        update.setVisible(reqVO.getVisible());
        bizProductCommentMapper.updateById(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replyComment(ProductCommentReplyIn reqVO, Long userId) {
        ProductCommentDTO exists = validateCommentExists(reqVO.getId());
        ProductCommentDTO update = new ProductCommentDTO();
        update.setId(exists.getId());
        update.setReplyTime(new Date());
        update.setReplyUserId(userId);
        update.setReplyStatus(ProductConstants.COMMENT_REPLY_STATUS_REPLIED);
        update.setReplyContent(reqVO.getReplyContent());
        bizProductCommentMapper.updateById(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createComment(ProductCommentCreateIn reqVO) {
        ProductSkuDTO sku = bizProductSkuMapper.selectById(reqVO.getSkuId());
        if (sku == null) {
            throw new BizException(ProductConstants.SKU_NOT_EXISTS);
        }
        ProductSpuDTO spu = bizProductSpuMapper.selectById(reqVO.getSpuId());
        if (spu == null) {
            throw new BizException(ProductConstants.SPU_NOT_EXISTS);
        }

        ProductCommentDTO comment = ProductCommentConvert.INSTANCE.toDTO(reqVO);
        comment.setSpuName(spu.getName());
        comment.setVisible(reqVO.getVisible() == null ? ProductConstants.DEFAULT_COMMENT_VISIBLE : reqVO.getVisible());
        comment.setReplyStatus(ProductConstants.DEFAULT_ZERO);
        bizProductCommentMapper.insert(comment);
    }

    private ProductCommentDTO validateCommentExists(Long id) {
        ProductCommentDTO comment = bizProductCommentMapper.selectById(id);
        if (comment == null) {
            throw new BizException(ProductConstants.COMMENT_NOT_EXISTS);
        }
        return comment;
    }
}
