package com.followba.store.common.constent;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public interface ErrorCode {

    /**
     * 状态码
     * @return int
     */
    int code();

    /**
     * 描述
     *
     * @return string
     */
    String msg();


    /**
     * 描述
     *
     * @return string
     */
    default String enMsg(){
        return this.msg();
    }
}
