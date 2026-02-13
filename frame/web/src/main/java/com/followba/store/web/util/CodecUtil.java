//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.followba.store.web.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CodecUtil {
    private static final String DEFAULT_URL_ENCODING = "UTF-8";
    private static final char[] ALPHABET = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    static final int[] INVERTED_ALPHABET = new int[128];
    static final String INIT_STR = "00000000000000000000000000000000000000000000000000000000000000000000";
    static final BigInteger MODULUS;

    static {
        int i;
        for(i = 0; i < 128; ++i) {
            INVERTED_ALPHABET[i] = -1;
        }

        for(i = 65; i <= 90; ++i) {
            INVERTED_ALPHABET[i] = i - 65 + 10;
        }

        for(i = 48; i <= 57; ++i) {
            INVERTED_ALPHABET[i] = i - 48;
        }

        MODULUS = new BigInteger("36");
    }

    public CodecUtil() {
    }

    public static String hexEncode(byte[] data) {
        return data != null && data.length != 0 ? Hex.encodeHexString(data) : null;
    }

    public static byte[] hexDecode(String str) throws DecoderException {
        byte[] result = (byte[])null;
        return str != null && str.length() != 0 ? Hex.decodeHex(str.toCharArray()) : result;
    }

    public static String base64Encode(byte[] binaryData) {
        return binaryData != null && binaryData.length != 0 ? Base64.encodeBase64String(binaryData) : null;
    }

    public static String base64UrlEncode(byte[] binaryData) {
        return binaryData != null && binaryData.length != 0 ? Base64.encodeBase64URLSafeString(binaryData) : null;
    }

    public static byte[] base64Decode(String base64String) {
        return base64String != null && base64String.length() != 0 ? Base64.decodeBase64(base64String) : null;
    }

    public static String base36Encode(String hexStr) {
        if (hexStr != null && hexStr.length() != 0) {
            StringBuffer sb = new StringBuffer();
            BigInteger bi = new BigInteger(hexStr, 16);
            BigInteger d = bi;
            BigInteger m = BigInteger.ZERO;

            while(!BigInteger.ZERO.equals(d)) {
                m = d.mod(MODULUS);
                d = d.divide(MODULUS);
                sb.insert(0, ALPHABET[m.intValue()]);
            }

            return sb.toString();
        } else {
            return null;
        }
    }

    public static String base36Encode(String hexStr, int length) {
        if (hexStr != null && hexStr.length() != 0) {
            if (length > 64) {
                throw new IllegalArgumentException("参数错误（length不能超过64）:param length = " + length);
            } else {
                String str = base36Encode(hexStr);
                if (length > str.length()) {
                    str = "00000000000000000000000000000000000000000000000000000000000000000000".concat(str);
                    str = str.substring(str.length() - length);
                } else {
                    str = str.substring(str.length() - length);
                }

                return str;
            }
        } else {
            return null;
        }
    }

    public static String base36Decode(String base36Str) {
        if (base36Str != null && base36Str.length() != 0) {
            if (!isValidBase32Str(base36Str)) {
                throw new IllegalArgumentException("base36字符串格式错误:\"" + base36Str + "\"");
            } else {
                base36Str = base36Str.toUpperCase();
                BigInteger bi = BigInteger.ZERO;
                char[] chars = base36Str.toCharArray();

                for(int i = 0; i < base36Str.length(); ++i) {
                    char c = chars[i];
                    int n = INVERTED_ALPHABET[c];
                    bi = bi.multiply(MODULUS).add(new BigInteger("" + n));
                }

                return bi.toString(16);
            }
        } else {
            return null;
        }
    }

    public static String urlEncode(String s) {
        return urlEncode(s, "UTF-8");
    }

    public static String urlEncode(String s, String enc) {
        if (s != null && s.trim().length() != 0) {
            try {
                return URLEncoder.encode(s, enc);
            } catch (UnsupportedEncodingException var3) {
                throw new IllegalArgumentException("urlEncode(" + s + "," + enc + ")，URL编码异常!", var3);
            }
        } else {
            return null;
        }
    }

    public static String urlDecode(String encodedUrl) {
        return urlDecode(encodedUrl, "UTF-8");
    }

    public static String urlDecode(String s, String enc) {
        if (s != null && s.trim().length() != 0) {
            try {
                return URLDecoder.decode(s, enc);
            } catch (UnsupportedEncodingException var3) {
                throw new IllegalArgumentException("urlEncode(" + s + "," + enc + ")，URL解码异常!", var3);
            }
        } else {
            return s;
        }
    }

    private static boolean isValidBase32Str(String str) {
        char[] chars = str.toCharArray();

        for(int i = 0; i < chars.length; ++i) {
            if (!isValidBase32Char(chars[i])) {
                return false;
            }
        }

        return true;
    }

    private static boolean isValidBase32Char(char c) {
        if (c >= 0 && c < 128) {
            return INVERTED_ALPHABET[c] != -1;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws DecoderException {
        System.out.println(urlDecode("http://test.api1.wangpiao.com/?UserName=163TestUser&Target=Sell_ApplyTicketSafe&sign=16c07030a07889410396f57f282458fd&SID=0000059770&PayType=9990&AID=0&Mobile=15810437404&MsgType=2&Amount=30.00&GoodsType=1&STime=2012-10-25+15%3A32%3A49BSign=1dfb9cca3ae472edce010c4a470a4be0"));
    }
}
