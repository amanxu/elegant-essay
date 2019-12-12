package com.elegant.essay.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-12 23:04
 */
@Slf4j
public class MD5Util {

    public static final String algorithm = "MD5";
    public static final String charsetName = "UTF-8";

    /**
     * 生成MD5
     *
     * @param message
     * @return
     */
    public static String md5Hex(String message) {
        try {
            // 创建一个md5算法对象
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] messageByte = message.getBytes(charsetName);
            // 获得MD5字节数组,16*8=128位
            byte[] md5Byte = md.digest(messageByte);
            // 转换为16进制字符串
            return bytesToHex(md5Byte);
        } catch (Exception e) {
            log.error("MD5Util getMD5 Exception：{}", e);
            return null;
        }
    }

    /**
     * 二进制转十六进制
     *
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if (num < 0) {
                num += 256;
            }
            if (num < 16) {
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}