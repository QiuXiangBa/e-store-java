package com.followba.store.web.template;

import com.followba.store.web.service.WebLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author 祥霸
 * @since 1.0.0
 */
@Component
public class WebLoginTemplate {

    private final WebLoginService webLoginService;

    public WebLoginTemplate(@Autowired(required = false) WebLoginService webLoginService) throws Exception {
        if(Objects.isNull(webLoginService)){
            throw new Exception("IOC容器中没有 com.followba.store.web.service.WebLoginService 的 Bean 对象");
        }
        this.webLoginService = webLoginService;
    }

    public String getUserIdByToken(String token){
        return webLoginService.getUserIdByToken(token);
    }
}
