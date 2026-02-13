package com.followba.store.web.handler;

import com.followba.store.common.constent.CodeEnum;
import com.followba.store.common.exception.BizException;
import com.followba.store.common.resp.Out;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常拦截器
 *
 * @author 祥霸
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
@Order(1)
public class FrameGlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Out<String> bizExceptionHandler(HttpServletRequest request, BizException e) {
        log.error("uri: {}, error: ", request.getRequestURI(), e);
        return Out.build(e.getCode(), e.getMessage(), e.getEnMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Out<String> bindExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getFieldError();
        log.error("uri: {}, error: ", request.getRequestURI(), e);
        return Out.build(CodeEnum.PARAMS_BAD.code(), Objects.isNull(objectError)?"" : objectError.getDefaultMessage());
    }

    @ExceptionHandler(Throwable.class)
    public Out<String> exceptionHandler(HttpServletRequest request, Throwable e) {
        log.error("uri: {}, error: ", request.getRequestURI(), e);
        return Out.build(CodeEnum.ERROR.code(), e.getMessage());
    }


}
