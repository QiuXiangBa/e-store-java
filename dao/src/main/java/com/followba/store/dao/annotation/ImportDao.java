package com.followba.store.dao.annotation;


import com.followba.store.dao.DaoSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DaoSelector.class})
@Documented
public @interface ImportDao {
}
