package com.followba.store.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 祥霸
 * @since 1.0.0
 */
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {
    /**
     * mybatis-plus 配置
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 添加分页插件
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor();
        // 设置请求的页面大于最大页后操作，true调回到首页，false继续请求。默认false
        pageInterceptor.setOverflow(false);
        interceptor.addInnerInterceptor(pageInterceptor);

        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        // 设置数据库类型
        pageInterceptor.setDbType(DbType.MYSQL);
        return interceptor ;
    }

}
