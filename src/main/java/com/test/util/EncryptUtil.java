package com.test.util;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
    /**
     * @param content  加密内容
     * @param key      加密秘钥8位
     * @return
     * @throws Exception
     */
    public static String encrypt(String content,String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        String encryptString = Base64.encodeBase64String(encrypted);
        System.out.println(encryptString);
        return encryptString;
    }

    /**
     *
     * @param base64Content  base64编码好的密文
     * @param key            接密秘钥8位
     * @return
     * @throws Exception
     */
    public static String decrypt(String base64Content,String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] content = Base64.decodeBase64(base64Content);
        byte[] encrypted = cipher.doFinal(content);
        String decryptString = new String(encrypted);
        System.out.println(decryptString);
        return decryptString;
    }
}
