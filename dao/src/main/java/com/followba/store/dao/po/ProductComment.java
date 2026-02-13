package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 商品评论
 */
@Data
@TableName(value = "product_comment")
public class ProductComment {
    /**
     * 评论编号，主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评价人的用户编号关联 MemberUserDO 的 id 编号
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 评价人名称
     */
    @TableField(value = "user_nickname")
    private String userNickname;

    /**
     * 评价人头像
     */
    @TableField(value = "user_avatar")
    private String userAvatar;

    /**
     * 是否匿名
     */
    @TableField(value = "anonymous")
    private Boolean anonymous;

    /**
     * 交易订单编号关联 TradeOrderDO 的 id 编号
     */
    @TableField(value = "order_id")
    private Long orderId;

    /**
     * 交易订单项编号关联 TradeOrderItemDO 的 id 编号
     */
    @TableField(value = "order_item_id")
    private Long orderItemId;

    /**
     * 商品 SPU 编号关联 ProductSpuDO 的 id
     */
    @TableField(value = "spu_id")
    private Long spuId;

    /**
     * 商品 SPU 名称
     */
    @TableField(value = "spu_name")
    private String spuName;

    /**
     * 商品 SKU 编号关联 ProductSkuDO 的 id 编号
     */
    @TableField(value = "sku_id")
    private Long skuId;

    /**
     * 是否可见true:显示false:隐藏
     */
    @TableField(value = "visible")
    private Boolean visible;

    /**
     * 评分星级1-5分
     */
    @TableField(value = "scores")
    private Integer scores;

    /**
     * 描述星级1-5 星
     */
    @TableField(value = "description_scores")
    private Integer descriptionScores;

    /**
     * 服务星级1-5 星
     */
    @TableField(value = "benefit_scores")
    private Integer benefitScores;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 评论图片地址数组
     */
    @TableField(value = "pic_urls")
    private String picUrls;

    /**
     * 商家是否回复
     */
    @TableField(value = "reply_status")
    private Integer replyStatus;

    /**
     * 回复管理员编号关联 AdminUserDO 的 id 编号
     */
    @TableField(value = "reply_user_id")
    private Long replyUserId;

    /**
     * 商家回复内容
     */
    @TableField(value = "reply_content")
    private String replyContent;

    /**
     * 商家回复时间
     */
    @TableField(value = "reply_time")
    private Date replyTime;

    /**
     * 创建者
     */
    @TableField(value = "creator")
    private String creator;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(value = "updater")
    private String updater;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     * 租户编号
     */
    @TableField(value = "tenant_id")
    private Long tenantId;
}