package com.followba.store.admin.controller;

import com.followba.store.dao.constant.ProductConstants;
import com.followba.store.admin.service.ProductCommentService;
import com.followba.store.admin.vo.in.ProductCommentCreateIn;
import com.followba.store.admin.vo.in.ProductCommentPageIn;
import com.followba.store.admin.vo.in.ProductCommentReplyIn;
import com.followba.store.admin.vo.in.ProductCommentUpdateVisibleIn;
import com.followba.store.admin.vo.out.CommonBooleanRespVO;
import com.followba.store.admin.vo.out.ProductCommentRespVO;
import com.followba.store.common.resp.Out;
import com.followba.store.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/comment")
@Validated
public class ProductCommentController {

    @Resource
    private ProductCommentService productCommentService;

    @GetMapping("/page")
    public Out<PageResp<ProductCommentRespVO>> getCommentPage(@Valid ProductCommentPageIn reqVO) {
        return Out.success(productCommentService.getCommentPage(reqVO));
    }

    @PutMapping("/update-visible")
    public Out<CommonBooleanRespVO> updateCommentVisible(@Valid @RequestBody ProductCommentUpdateVisibleIn reqVO) {
        productCommentService.updateCommentVisible(reqVO);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @PutMapping("/reply")
    public Out<CommonBooleanRespVO> commentReply(@Valid @RequestBody ProductCommentReplyIn reqVO) {
        productCommentService.replyComment(reqVO, ProductConstants.DEFAULT_REPLY_USER_ID);
        return Out.success(CommonBooleanRespVO.ok());
    }

    @PostMapping("/create")
    public Out<CommonBooleanRespVO> createComment(@Valid @RequestBody ProductCommentCreateIn reqVO) {
        productCommentService.createComment(reqVO);
        return Out.success(CommonBooleanRespVO.ok());
    }
}
