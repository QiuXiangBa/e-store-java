package com.followba.store.product.annotation;



import com.followba.store.product.ProductSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ProductSelector.class})
@Documented
public @interface ImportProduct {
}
