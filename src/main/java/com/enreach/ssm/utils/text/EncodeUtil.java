package com.enreach.ssm.utils.text;

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * string/url -> hex/base64 编解码工具集(via guava BaseEncoding)
 */
public class EncodeUtil {

    public static final String UTF_8_NAME = StandardCharsets.UTF_8.name();

    /**
     * URL 编码, Encode默认为UTF-8.
     * <p>
     * 转义后的URL可作为URL中的参数
     */
    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, UTF_8_NAME);
        } catch (UnsupportedEncodingException ignored) { // NOSONAR
            // this exception is only for detecting and handling invalid inputs
            return null;
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8. 转义后的URL可作为URL中的参数
     */
    public static String urlDecode(String part) {
        try {
            return URLDecoder.decode(part, UTF_8_NAME);
        } catch (UnsupportedEncodingException e) { // NOSONAR
            // this exception is only for detecting and handling invalid inputs
            return null;
        }
    }

    /**
     * Hex编码, 将byte[]编码为String，默认为ABCDEF为大写字母.
     */
    public static String encodeHex(byte[] input) {
        return BaseEncoding.base16().encode(input);
    }

    /**
     * Hex解码, 将String解码为byte[].
     * <p>
     * 字符串有异常时抛出IllegalArgumentException.
     */
    public static byte[] decodeHex(CharSequence input) {
        return BaseEncoding.base16().decode(input);
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) {
        return BaseEncoding.base64().encode(input);
    }

    /**
     * Base64解码.
     * <p>
     * 如果字符不合法，抛出IllegalArgumentException
     */
    public static byte[] decodeBase64(CharSequence input) {
        return BaseEncoding.base64().decode(input);
    }

    /**
     * Base64编码, URL安全.(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     */
    public static String encodeBase64UrlSafe(byte[] input) {
        return BaseEncoding.base64Url().encode(input);
    }

    /**
     * Base64解码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     * <p>
     * 如果字符不合法，抛出IllegalArgumentException
     */
    public static byte[] decodeBase64UrlSafe(CharSequence input) {
        return BaseEncoding.base64Url().decode(input);
    }
}

