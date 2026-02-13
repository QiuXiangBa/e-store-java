/*
 * AESUtil.java
 * Copyright 2021 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.followba.store.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 祥霸
 * @since 1.0.0
 */
@Log4j2
public class AESUtil {

    private static final ThreadLocal<Map<String, Cipher>> CIPHER_THREAD_LOCAL_EN = new ThreadLocal<>();

    private static final ThreadLocal<Map<String, Cipher>> CIPHER_THREAD_LOCAL_DE = new ThreadLocal<>();

    /**
     * 加密
     *
     * @param sSrc 加密内容
     * @param aesKey 密钥
     * @return str
     */
    public static String encrypt(String sSrc, String aesKey) throws Exception {
        if (StringUtils.isBlank(sSrc) || StringUtils.isBlank(aesKey)) {
            return null;
        }
        Map<String, Cipher> cipherMap = CIPHER_THREAD_LOCAL_EN.get();

        if (cipherMap == null) {
            cipherMap = new HashMap<>(10);
            CIPHER_THREAD_LOCAL_EN.set(cipherMap);
        }
        Cipher aesCipher = cipherMap.get(aesKey);
        if (aesCipher == null) {
            byte[] raw = aesKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            cipherMap.put(aesKey, aesCipher);
        }

        return Base64.encodeUrlSafe(aesCipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 解密
     *
     * @param sSrc 内容
     * @param aesKey 密钥
     * @return str
     */
    public static String decrypt(String sSrc, String aesKey) throws Exception  {
        if (StrUtil.isBlank(sSrc) || StrUtil.isBlank(aesKey)) {
            return "";
        }

        log.info("sSrc:{}", sSrc);

        Map<String, Cipher> cipherMap = CIPHER_THREAD_LOCAL_DE.get();
        if (cipherMap == null) {
            cipherMap = new HashMap<>(10);
            CIPHER_THREAD_LOCAL_DE.set(cipherMap);
        }
        Cipher aesCipher = cipherMap.get(aesKey);
        if (aesCipher == null) {
            byte[] raw = aesKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, skeySpec);
            cipherMap.put(aesKey, aesCipher);
        }
        return new String(aesCipher.doFinal(Base64.decode(sSrc)), StandardCharsets.UTF_8);
    }
}
