package com.followba.store.auth.annotation;




import com.followba.store.auth.AuthSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({AuthSelector.class})
@Documented
public @interface ImportAuth {
}
