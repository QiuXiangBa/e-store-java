package com.followba.store.dao.constant;

import com.followba.store.common.constent.ErrorCode;

public enum ProductConstants implements ErrorCode {

    BRAND_NOT_EXISTS(61001, "品牌不存在"),
    BRAND_NAME_EXISTS(61002, "品牌名称已存在"),
    BRAND_DISABLED(61003, "品牌已禁用"),

    CATEGORY_NOT_EXISTS(61101, "分类不存在"),
    CATEGORY_PARENT_NOT_EXISTS(61102, "父分类不存在"),
    CATEGORY_PARENT_NOT_FIRST_LEVEL(61103, "父分类必须是一级分类"),
    CATEGORY_EXISTS_CHILDREN(61104, "分类下仍存在子分类"),
    CATEGORY_HAVE_BIND_SPU(61105, "分类下存在商品，不能删除"),
    CATEGORY_DISABLED(61106, "分类已禁用"),
    CATEGORY_LEVEL_ERROR(61107, "商品分类层级不合法"),
    CATEGORY_SORT_BATCH_EMPTY(61108, "批量排序列表不能为空"),
    CATEGORY_SORT_BATCH_SIZE_EXCEED(61109, "批量排序数量超限"),
    CATEGORY_SORT_ID_DUPLICATE(61110, "批量排序存在重复分类ID"),
    CATEGORY_SORT_INVALID(61111, "分类排序参数不合法"),

    PROPERTY_NOT_EXISTS(61201, "规格属性不存在"),
    PROPERTY_VALUE_NOT_EXISTS(61202, "规格值不存在"),
    PROPERTY_NAME_EXISTS(61203, "规格属性名称已存在"),
    PROPERTY_VALUE_NAME_EXISTS(61204, "规格值名称已存在"),
    PROPERTY_HAS_VALUE(61205, "该规格下仍有规格值，不能删除"),

    SKU_NOT_EXISTS(61301, "商品 SKU 不存在"),
    SKU_PROPERTIES_DUPLICATED(61302, "SKU 属性重复"),
    SKU_DUPLICATED(61303, "SKU 组合重复"),
    SKU_PROPERTIES_NOT_EXISTS(61304, "SKU 属性不存在"),
    SKU_ATTR_NUMBERS_MUST_BE_EQUALS(61305, "每个 SKU 的属性数量必须一致"),

    SPU_NOT_EXISTS(61401, "商品 SPU 不存在"),
    SPU_NOT_ENABLE(61402, "商品已下架"),
    SPU_NOT_RECYCLE(61403, "商品不在回收站，不能删除"),
    SPU_STATUS_INVALID(61404, "商品状态不合法"),

    COMMENT_NOT_EXISTS(61501, "商品评论不存在");

    private final int code;
    private final String msg;

    ProductConstants(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }

    public static final int DEFAULT_ZERO = 0;

    public static final int DEFAULT_ONE = 1;

    public static final boolean DEFAULT_COMMENT_VISIBLE = true;

    public static final int SINGLE_SKU_SIZE = 1;

    public static final long DEFAULT_REPLY_USER_ID = 0L;

    public static final int COMMENT_REPLY_STATUS_REPLIED = 1;

    public static final int CATEGORY_MIN_LEVEL = 2;

    public static final int PROPERTY_SIMPLE_ENABLED_STATUS = 0;

    public static final int PROPERTY_VALUE_SIMPLE_ENABLED_STATUS = 0;

    public static final int CATEGORY_SORT_BATCH_MAX_SIZE = 200;

    public static final int ALERT_STOCK = 10;

    public static final Long SKU_DEFAULT_PROPERTY_ID = 0L;

    public static final String SKU_DEFAULT_PROPERTY_NAME = "默认";

    public static final Long SKU_DEFAULT_PROPERTY_VALUE_ID = 0L;

    public static final String SKU_DEFAULT_PROPERTY_VALUE_NAME = "默认";
}
