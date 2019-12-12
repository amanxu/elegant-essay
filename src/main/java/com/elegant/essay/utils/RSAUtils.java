package com.elegant.essay.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.TreeMap;

/**
 * @description: RSA 加解密，RSA encrypt|decrypt
 * @author: xiaoxu.nie
 * @date: 2019-02-14 17:04
 */
@Slf4j
public class RSAUtils {

    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static KeyFactory keyFactory = null;
    private static Cipher cipher = null;

    static {
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException:{}", e);
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException:{}", e);
        }
    }


    /**
     * Encode string.
     *
     * @param msg    the msg
     * @param pubKey the pub key
     * @return the string
     * @throws Exception the exception
     */
    public static String encode(String msg, String pubKey) {
        byte[] pubKeyBytes = hexStrToBytes(pubKey);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(pubKeyBytes);
        try {
            PublicKey publicKey = keyFactory.generatePublic(spec);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] data = msg.getBytes(StandardCharsets.UTF_8);
            int inputLen = data.length;

            return bytesToHexStr(sectionData(inputLen, cipher, data, MAX_ENCRYPT_BLOCK));
        } catch (InvalidKeyException e) {
            log.error("InvalidKeyException:{}", e);
        } catch (InvalidKeySpecException e) {
            log.error("InvalidKeySpecException:{}", e);
        } catch (Exception e) {
            log.error("Exception:{}", e);
        }
        return null;
    }


    /**
     * Decode string.
     *
     * @param hexMsg the hex msg
     * @param priKey the pri key
     * @return the string
     * @throws Exception the exception
     */
    public static String decode(String hexMsg, String priKey) throws Exception {
        byte[] priKeyBytes = hexStrToBytes(priKey);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(priKeyBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(spec);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedData = hexStrToBytes(hexMsg);
        int inputLen = encryptedData.length;
        return new String(sectionData(inputLen, cipher, encryptedData, MAX_DECRYPT_BLOCK), StandardCharsets.UTF_8);
    }

    private static byte[] sectionData(int inputLen, Cipher cipher, byte[] data, int block) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > block) {
                cache = cipher.doFinal(data, offSet, block);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * block;
        }
        byte[] processedData = out.toByteArray();
        out.close();
        return processedData;
    }

    private static String bytesToHexStr(byte[] bytes) {
        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }
        byte[] byteContent = Base64.getEncoder().encode(bytes);
        return new String(byteContent, StandardCharsets.UTF_8);
    }

    private static byte[] hexStrToBytes(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            return null;
        }
        return Base64.getDecoder().decode(hexString);
    }

    /**
     * 解析treeMap生成签名字符串
     *
     * @param paramMap
     * @return
     */
    public static String getMsgByMap(TreeMap paramMap) {
        if (CollectionUtils.isEmpty(paramMap)) {
            return null;
        }
        StringBuilder sbu = new StringBuilder();
        paramMap.forEach((k, v) -> {
            sbu.append(k).append("=").append(v).append("&");
        });
        String msg = sbu.toString().toString();

        return msg.substring(0, msg.length() - 1);
    }

    /**
     * 加密treeMap请求参数，根据treeMap默认排序规则生成加密串
     *
     * @param paramMap
     * @param pubKey
     * @return
     */
    public static String encodeMsg(TreeMap paramMap, String pubKey) {
        String msg = getMsgByMap(paramMap);
        if (StringUtils.isEmpty(msg)) {
            return null;
        }
        return encode(msg, pubKey);
    }

    public static void main(String[] args) {
        // 请求参数Map（无需排序直接放入map中，treeMap根据默认排序规则排序）
        TreeMap<String, Object> paramMap = new TreeMap<>();
        paramMap.put("timestamp", System.currentTimeMillis());
        /*paramMap.put("pageNo", 1);
        paramMap.put("pageSize", 10);*/

        // 加密公钥
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHNUXVU5A12fP1r+K6V/lli4jjO9R3M2HpGqAI0qDzDUCSMNQT0NMNPPZ72IQL4UFLijWG1X/gGnMyNhtAzX64i4KJhmtGe5pql0pVYayii+SX5afp+6NdFYaEXIQLqwWDFEWahzq74pEF0BKOWD3KIllFOI6y0GhhIwBfs/l4hwIDAQAB";
        String encodeMsg = null;
        try {
            // 生成加密字符串
            encodeMsg = encodeMsg(paramMap, pubKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(encodeMsg);
    }
}

