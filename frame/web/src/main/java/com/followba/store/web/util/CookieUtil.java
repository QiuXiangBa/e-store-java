/*
 * CookieUtil.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.web.util;


import com.followba.store.web.pojo.UserCookie;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 祥霸
 * @version 1.0.0
 */

@Slf4j
public abstract class CookieUtil {

    protected static final String ANCIENT_DATE;
    protected static final ThreadLocal<DateFormat> COOKIE_DATE_FORMAT = ThreadLocal.withInitial(() -> {
        DateFormat df = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z", Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df;
    });

    static {
        ANCIENT_DATE = COOKIE_DATE_FORMAT.get().format(new Date(10000L));
    }

    /**
     * 得到cookie
     */
    public static String getCookie(HttpServletRequest request, String cookieName) {

        try {
            String cookieValue = null;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ((cookie.getName()).equals(cookieName)) {
                    cookieValue = cookie.getValue();
                    break;
                }
            }
            return CodecUtil.urlDecode(cookieValue);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 将所有cookie设置到响应中去
     */
    public static void applyUserCookies(HttpServletResponse response, List<UserCookie> userCookies) {
        for (UserCookie userCookie : userCookies) {
            // 由于2.5 api 不支持httpOnly，采用如下方式
            response.addHeader("Set-Cookie", generateCookieHeader(userCookie));
        }
    }

    private static String generateCookieHeader(UserCookie cookie) {
        StringBuffer header = new StringBuffer();
        header.append(cookie.getName());
        header.append('=');
        String value = cookie.getValue();
        if (value != null && value.length() > 0) {
            header.append(value);
        }

        int maxAge = cookie.getMaxAge();
        if (maxAge > -1) {
            header.append("; Max-Age=");
            header.append(maxAge);
            header.append("; Expires=");
            if (maxAge == 0) {
                header.append(ANCIENT_DATE);
            } else {
                ((DateFormat)COOKIE_DATE_FORMAT.get()).format(new Date(System.currentTimeMillis() + (long)maxAge * 1000L), header, new FieldPosition(0));
            }
        }

        String domain = cookie.getDomain();
        if (domain != null && domain.length() > 0) {
            header.append("; Domain=");
            header.append(domain);
        }

        String path = cookie.getPath();
        if (path != null && path.length() > 0) {
            header.append("; Path=");
            header.append(path);
        }
        if (cookie.isHttpOnly()) {
            header.append("; HttpOnly");
        }
        //SameSite=None 只有在测试环境才加
        if(cookie.isCookieSameSite()) {
            // TODO SameSite 测试 start
            header.append("; Secure; SameSite=None");
            // TODO SameSite 测试 end
        }

        return header.toString();
    }

    public static Map<String, String> getCookieMap(HttpServletRequest request) {
        Map<String, String> cookieMap = new HashMap<>();
        if (null == request) {
            return cookieMap;
        }
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieMap;
    }
}
