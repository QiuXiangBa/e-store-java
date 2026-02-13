package com.followba.store.admin.service;

import com.followba.store.admin.vo.in.ProductCommentCreateIn;
import com.followba.store.admin.vo.in.ProductCommentPageIn;
import com.followba.store.admin.vo.in.ProductCommentReplyIn;
import com.followba.store.admin.vo.in.ProductCommentUpdateVisibleIn;
import com.followba.store.admin.vo.out.ProductCommentRespVO;
import com.followba.store.common.resp.PageResp;

public interface ProductCommentService {

    PageResp<ProductCommentRespVO> getCommentPage(ProductCommentPageIn reqVO);

    void updateCommentVisible(ProductCommentUpdateVisibleIn reqVO);

    void replyComment(ProductCommentReplyIn reqVO, Long userId);

    void createComment(ProductCommentCreateIn reqVO);
}
