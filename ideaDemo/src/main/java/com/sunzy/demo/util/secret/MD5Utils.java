package com.sunzy.demo.util.secret;

/**
 * @author sunzy
 * @date 2020/8/4
 */
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

public class MD5Utils {

    // 禁止修改此字符串,如果修改，将出现一系列加密算法问题
    private final static String SALT = "#@*A12^c0+";

    /**
     * 使用MD5加密算法对字符串加密
     * @param oriCode  原始字符串
     * @return 加密字符串
     */
    public static String encrypt(String oriCode) {
        return encrypt(oriCode, SALT);
    }

    /**
     * 对字符串使用加盐的方式进行MD5加密
     * @param oriCode 原始字符串
     * @param salt    加盐字符串
     * @return 加密字符串
     */
    public static String encrypt(String oriCode, String salt) {
        if (!StringUtils.isEmpty(oriCode) && !StringUtils.isEmpty(salt)) {
            return DigestUtils.md5Hex(oriCode + "{" + salt + "}");
        }
        return oriCode;
    }
}
