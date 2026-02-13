package com.followba.store.admin.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCommentCreateIn {

    @NotNull(message = "用户 id 不能为空")
    private Long userId;

    @NotNull(message = "spuId 不能为空")
    private Long spuId;

    @NotNull(message = "skuId 不能为空")
    private Long skuId;

    private String userNickname;

    private String userAvatar;

    private Boolean anonymous;

    private Long orderId;

    private Long orderItemId;

    private Byte scores;

    private Byte descriptionScores;

    private Byte benefitScores;

    private String content;

    private String picUrls;

    private Boolean visible;
}
