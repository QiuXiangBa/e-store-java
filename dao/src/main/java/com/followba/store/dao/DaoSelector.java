package com.followba.store.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.followba.store.dao.mapper")
@ComponentScan
public class DaoSelector {
}
