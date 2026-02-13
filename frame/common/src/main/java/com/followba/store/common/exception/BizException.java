package com.followba.store.common.exception;

import com.followba.store.common.constent.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 *
 * @author 祥霸
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {
    private Integer code;

    private String message;

    private String enMessage;

    public BizException(ErrorCode errorCode) {
        super(errorCode.code() + ":" + errorCode.msg());
        this.code = errorCode.code();
        this.message = errorCode.msg();
        this.enMessage = errorCode.enMsg();
    }

    public BizException(ErrorCode errorCode, String msg) {
        super(errorCode.code() + ":" + errorCode.msg());
        this.code = errorCode.code();
        this.message = msg;
        this.enMessage = errorCode.enMsg();
    }

    public BizException(ErrorCode errorCode, String msg, String enMessage) {
        super(errorCode.code() + ":" + errorCode.msg());
        this.code = errorCode.code();
        this.message = msg;
        this.enMessage = enMessage;
    }
}
