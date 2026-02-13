package com.followba.store.web;


import com.followba.store.web.config.WebProperties;
import com.followba.store.web.pojo.UserCookie;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WebService {

    @Resource
    private WebProperties webProperties;

    /**
     * 获取toke cookie
     * @param token token
     * @param domainArr 域名
     * @return {@link UserCookie}
     */
    public List<UserCookie> getTokenCookies(String token, String...domainArr) {
        List<UserCookie> cookies = new ArrayList<>();
        for (String domain : domainArr) {
            UserCookie tokenCookie = new UserCookie();
            tokenCookie.setDomain(domain);
            tokenCookie.setHttpOnly(true);
            tokenCookie.setMaxAge(3600 * 24 * 7); // 有效期设置为一周
            tokenCookie.setPath("/");
            tokenCookie.setName(webProperties.getHeadTokenName());
            tokenCookie.setValue(token);
            cookies.add(tokenCookie);
        }

        return cookies;
    }

}
