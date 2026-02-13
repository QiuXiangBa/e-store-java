package com.followba.store.admin.vo.out;

import lombok.Data;

import java.util.Date;

@Data
public class ProductCommentRespVO {

    private Long id;

    private Long userId;

    private String userNickname;

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

    private Date createTime;
}
