package com.sunzy.demo.util.secret;

/**
 * @author sunzy
 * @date 2020/8/4
 */
import org.springframework.util.Base64Utils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES对称加密，根据秘钥和内容就可以加密和解密
 * 使用场景：触点中心手机号信息加密解密
 */
public class AesUtils {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    /**
     * AES 加密操作
     * @param content 待加密内容
     * @param key     加密密钥
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));// 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(byteContent);// 加密
            return new String(Base64Utils.encode(result));//通过Base64转码返回
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * AES 解密操作
     * @param content 待解密内容
     * @param key 秘钥
     * @return
     */
    public static String decrypt(String content, String key) {

        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
            //执行操作
            byte[] result = cipher.doFinal(Base64Utils.decode(content.getBytes()));
            return new String(result, "utf-8");
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String key) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            //AES 要求密钥长度为 128
            kg.init(128, new SecureRandom(key.getBytes()));
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException ex) {
        }
        return null;
    }

    public static void main(String[] args) {
        String content = "hello,您好";
        //String key = "sde@5f98H*^hsff%dfs$r344&df8543*er";
        String key = "Nucc@cs.cmos2019";
        //System.out.println("content:" + content);
        String s1 = AesUtils.encrypt(content, key);
        String s2 = AesUtils.decrypt(s1, key);
        //System.out.println("加密后内容:" + s1);
        //System.out.println("解密后内容:" + s2);
    }
}
