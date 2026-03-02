package com.followba.store.payment.annotation;

import com.followba.store.dao.annotation.ImportDao;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用支付模块 / Enable payment module.
 */
@ImportDao
@ComponentScan
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportPayment {
}
