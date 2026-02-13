package com.followba.store.web.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author 祥霸
 * @since 1.0.1
 */
@Data
@ConfigurationProperties(
        prefix = "i-tool.auth"
)
public class WebProperties {

    /**
     * token 的 head名
     */
    private String headTokenName;


    /**
     * 排除获取用户信息的uri
     */
    private List<String> excludePath;

    /**
     * 排除没有登录跳转登录页的uri
     */
    private List<String> excludePathAuth;

}
