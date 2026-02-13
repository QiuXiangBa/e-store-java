package com.followba.store.dao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductCommentDTO {

    private Long id;

    private Long userId;

    private String userNickname;

    private String userAvatar;

    private Boolean anonymous;

    private Long orderId;

    private Long orderItemId;

    private Long spuId;

    private String spuName;

    private Long skuId;

    private Boolean visible;

    private Integer scores;

    private Integer descriptionScores;

    private Integer benefitScores;

    private String content;

    private String picUrls;

    private Integer replyStatus;

    private Long replyUserId;

    private String replyContent;

    private Date replyTime;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Boolean deleted;

    private Long tenantId;
}
