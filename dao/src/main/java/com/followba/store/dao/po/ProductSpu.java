package com.followba.store.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 商品spu
 */
@Data
@TableName(value = "product_spu")
public class ProductSpu {
    /**
     * 商品 SPU 编号，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 关键字
     */
    @TableField(value = "keyword")
    private String keyword;

    /**
     * 商品简介
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 商品详情
     */
    @TableField(value = "description")
    private String description;

    /**
     * 条形码
     */
    @TableField(value = "bar_code")
    private String barCode;

    /**
     * 商品分类编号
     */
    @TableField(value = "category_id")
    private Long categoryId;

    /**
     * 商品品牌编号
     */
    @TableField(value = "brand_id")
    private Integer brandId;

    /**
     * 商品封面图
     */
    @TableField(value = "pic_url")
    private String picUrl;

    /**
     * 商品轮播图地址
     * 数组，以逗号分隔
     * 最多上传15张
     */
    @TableField(value = "slider_pic_urls")
    private String sliderPicUrls;

    /**
     * 商品视频
     */
    @TableField(value = "video_url")
    private String videoUrl;

    /**
     * 排序字段
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 商品状态: 0 上架（开启） 1 下架（禁用）-1 回收
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 规格类型：0 单规格 1 多规格
     */
    @TableField(value = "spec_type")
    private Integer specType;

    /**
     * 商品价格，单位使用：分
     */
    @TableField(value = "price")
    private Integer price;

    /**
     * 市场价，单位使用：分
     */
    @TableField(value = "market_price")
    private Integer marketPrice;

    /**
     * 成本价，单位： 分
     */
    @TableField(value = "cost_price")
    private Integer costPrice;

    /**
     * 库存
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 物流配置模板编号
     */
    @TableField(value = "delivery_template_id")
    private Long deliveryTemplateId;

    /**
     * 是否热卖推荐: 0 默认 1 热卖
     */
    @TableField(value = "recommend_hot")
    private Boolean recommendHot;

    /**
     * 是否优惠推荐: 0 默认 1 优选
     */
    @TableField(value = "recommend_benefit")
    private Boolean recommendBenefit;

    /**
     * 是否精品推荐: 0 默认 1 精品
     */
    @TableField(value = "recommend_best")
    private Boolean recommendBest;

    /**
     * 是否新品推荐: 0 默认 1 新品
     */
    @TableField(value = "recommend_new")
    private Boolean recommendNew;

    /**
     * 是否优品推荐
     */
    @TableField(value = "recommend_good")
    private Boolean recommendGood;

    /**
     * 赠送积分
     */
    @TableField(value = "give_integral")
    private Integer giveIntegral;

    /**
     * 赠送的优惠劵编号的数组
     */
    @TableField(value = "give_coupon_template_ids")
    private String giveCouponTemplateIds;

    /**
     * 分销类型
     */
    @TableField(value = "sub_commission_type")
    private Integer subCommissionType;

    /**
     * 活动显示排序0=默认, 1=秒杀，2=砍价，3=拼团
     */
    @TableField(value = "activity_orders")
    private String activityOrders;

    /**
     * 商品销量
     */
    @TableField(value = "sales_count")
    private Integer salesCount;

    /**
     * 虚拟销量
     */
    @TableField(value = "virtual_sales_count")
    private Integer virtualSalesCount;

    /**
     * 商品点击量
     */
    @TableField(value = "browse_count")
    private Integer browseCount;

    @TableField(value = "creator")
    private String creator;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "updater")
    private String updater;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "deleted")
    private Boolean deleted;

    @TableField(value = "tenant_id")
    private Long tenantId;
}