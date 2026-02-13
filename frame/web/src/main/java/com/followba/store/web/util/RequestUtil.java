/*
 * RequestUtil.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.web.util;

import com.followba.store.web.constent.DistCommonConstant;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author 祥霸
 * @since 1.0.0
 */
public class RequestUtil {

	public static String getLastIp(HttpServletRequest request) {

		String rip = request.getRemoteAddr();
		String xff = request.getHeader("X-Forwarded-For");
		String ip;
		if (xff != null && xff.length() != 0) {
			int px = xff.lastIndexOf(',');
			if (px != -1) {
				ip = xff.substring(px + 1);
			} else {
				ip = xff;
			}
		} else {
			ip = rip;
		}
		return ip.trim();
	}

	public static Object getAttribute(String name) {

		HttpServletRequest request = DistCommonConstant.requestTL.get();
		if (request == null) {
			return request;
		}
		return request.getAttribute(name);
	}

	public static void setAttribute(String name, Object value) {

		HttpServletRequest request = DistCommonConstant.requestTL.get();
		if (request == null) {
			return;
		}
		request.setAttribute(name, value);
	}

	public static void removeAttribute(String name) {

		HttpServletRequest request = DistCommonConstant.requestTL.get();
		if (request == null) {
			return;
		}
		request.removeAttribute(name);
	}

	public static boolean containsKey(String name) {

		Object value = getAttribute(name);
		if (value != null) {
			return true;
		}
		return false;
	}

	public static boolean notContainsKey(String name) {

		Object value = getAttribute(name);
		if (value == null) {
			return true;
		}
		return false;
	}

	public static HttpServletRequest getRequest() {

		return DistCommonConstant.requestTL.get();
	}

	public static String getParameter(String name) {

		HttpServletRequest request = DistCommonConstant.requestTL.get();
		if (request == null) {
			return null;
		}
		return request.getParameter(name);
	}

	public static String getParameter(HttpServletRequest request, String name) {

        if (request == null) {
            return null;
        }
        return request.getParameter(name);
    }
}
