package com.qingshan.mall.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

@Slf4j
public class SecurityUtils {
    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPdVc6U8T6lrx1fVmxxzEPZg1UK86Jt0oZPmlJOfeIPNzHMBLlY8SAP9oEy4u+iklGuIdZH9yhm2Tn1BqUZmskeUy/LeN9XCyQvrB8rBaxZRSV3inUUkF1KbEdguqq3TVPqmecs8Zs1D2iNFbWJn5MizqEiVXIreL2FI7Tj0itxQIDAQAB";
    private static final String privateKesy = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI91VzpTxPqWvHV9WbHHMQ9mDVQrzom3Shk+aUk594g83McwEuVjxIA/2gTLi76KSUa4h1kf3KGbZOfUGpRmayR5TL8t431cLJC+sHysFrFlFJXeKdRSQXUpsR2C6qrdNU+qZ5yzxmzUPaI0VtYmfkyLOoSJVcit4vYUjtOPSK3FAgMBAAECgYAS1ydIir3g5zEDAR5pga6Ixi9T8Zjjb0X1AMRVEJ7Yrp2UL1Ub+TlSWpBods74jDLJbeygoNWaB819wNZozPafFlG3Qs1K+axYxugiGtxkyiVmYDQ+wAj44BViayqxHc6ndcZIfTLYcGQZ7ZzbK9RiJRgEcIusvWvQOotNAXW3oQJBAMP6pf1i9OAbiRe5VdovMYYFix9+Qb8Mb6QqsLTjcIW1rokLZq4QVivIOtVKzHp/gKZcJbQZE8WiPb5HDvSo330CQQC7ZOiadfOAdX04O+rq0HV5kbfcuN6ATHY/LLRZwjcVvjsgxeGB6LjmfKbSdQmuY1RP4UT4JvxRYPFBRBA/h2npAkBrbbmR9a3QL6ft1k2mhZo7IkzKHbbCmHmWX0RwVAC5ewdK3nIR1qi15VPC2fbO65fFGdwdJmX9P1tjM6aSHQQtAkEAr3QWePBw8diPSoSHOh29r/pobMfGw9dTa2j+33+BNeFr2Q6NSqXL4Tnas/gS/W1Q9o2TlshhvAwJNrN0hxbRaQJAOT67JnsuxLzhrYYYzwkCTwteNi5DKK3PFWL6hDPUbvecSwWK/3DQnO6l/D9FSLfckAuv9ak91xG9aqQQAH3bEQ==";
    private static final String seed = "yishi";

    /**
     * MD5加密
     */
    public static String getMD5(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            log.error("MD5加密失败", e);
            return "";
        }
    }

    /**
     * 获取UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * RSA创建密钥
     */
    public static String[] generateRsaKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            byte[] publicKey = rsaPublicKey.getEncoded();
            byte[] privateKey = rsaPrivateKey.getEncoded();
            // key[0]:公钥 key[1]:私钥
            String[] key = {Base64.getEncoder().encodeToString(publicKey),
                    Base64.getEncoder().encodeToString(privateKey)};//也可以转换为16进制形式的字符串 new BigInteger(1, publicKey).toString(16)
            return key;
        } catch (Exception e) {
            throw ServiceException.exception("RSA创建密钥失败");
        }
    }

    /**
     * RSA私钥加密
     */
    public static String encryptByRsaPrivate(String src) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKesy));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            String result = Base64.getEncoder().encodeToString(cipher.doFinal(src.getBytes(StandardCharsets.UTF_8)));
            return result;
        } catch (Exception e) {
            throw ServiceException.exception("RSA私钥加密失败");
        }
    }

    /**
     * 公钥解密
     */
    public static String decryptByRsaPublic(String src) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            String result = new String(cipher.doFinal(Base64.getDecoder().decode(src)), StandardCharsets.UTF_8);
            return result;
        } catch (Exception e) {
            throw ServiceException.exception("公钥解密失败");
        }
    }

    /**
     * 公钥加密
     */
    public static String encryptByRsaPublic(String src) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(src.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw ServiceException.exception("公钥加密失败");
        }
    }

    /**
     * 私钥解密
     */
    public static String decryptByRsaPrivate(String src) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKesy));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            String result = new String(cipher.doFinal(Base64.getDecoder().decode(src)), StandardCharsets.UTF_8);
            return result;
        } catch (Exception e) {
            throw ServiceException.exception("私钥解密失败");
        }
    }

    /**
     * RSA私钥签名
     */
    public static String signByRsaPrivate(String src) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKesy));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("MD5WithRSA");
            signature.initSign(privateKey);
            signature.update(src.getBytes(StandardCharsets.UTF_8));
            String result = Base64.getEncoder().encodeToString(signature.sign());
            return result;
        } catch (Exception e) {
            throw ServiceException.exception("RSA私钥签名失败");
        }
    }

    /**
     * 公钥验签
     */
    public static boolean verifyByRsaPublic(String src, String signResult) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Signature signature = Signature.getInstance("MD5WithRSA");
            signature.initVerify(publicKey);
            signature.update(src.getBytes(StandardCharsets.UTF_8));
            boolean bool = signature.verify(Base64.getDecoder().decode(signResult));
            return bool;
        } catch (Exception e) {
            log.error("公钥验签失败", e);
            return false;
        }
    }

    /**
     * AES加密
     */
    public static String encryptByAes(String src) {
        return aesHandle(src, Cipher.ENCRYPT_MODE);
    }

    /**
     * AES解密
     */
    public static String decryptByAes(String src) {
        return aesHandle(src, Cipher.DECRYPT_MODE);
    }

    /**
     * AES加解密
     */
    private static String aesHandle(String src, int opmode) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");//构造密钥生成器，指定为AES算法,不区分大小写
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed.getBytes(StandardCharsets.UTF_8));
            keygen.init(128, secureRandom);//初始化密钥生成器,生成一个128位的随机源,根据传入的字节数组
            SecretKey original_key = keygen.generateKey();//产生原始对称密钥
            byte[] raw = original_key.getEncoded();//获得原始对称密钥的字节数组
            SecretKey key = new SecretKeySpec(raw, "AES");//根据字节数组生成AES密钥
            Cipher cipher = Cipher.getInstance("AES");//根据指定算法AES自成密码器
            if (Cipher.ENCRYPT_MODE == opmode) {
                cipher.init(Cipher.ENCRYPT_MODE, key);//初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
                String result = Base64.getEncoder().encodeToString(cipher.doFinal(src.getBytes(StandardCharsets.UTF_8)));//将加密后的数据转换为字符串
                return result;
            } else {
                cipher.init(Cipher.DECRYPT_MODE, key);//初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
                String AES_encode = new String(cipher.doFinal(Base64.getDecoder().decode(src)), StandardCharsets.UTF_8);//将加密后的数据转换为字符串
                return AES_encode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据id获取无序码
     * id最大值十进制19位，二进制61位
     * 62位的二进制最大值的十进制为19位
     */
    public static String getDisorderCode(Long id, SerialBusType busType) {
        StringBuilder srcBinaryStr = new StringBuilder(Long.toBinaryString(id));//二进制字符串
        for (int i = srcBinaryStr.length(); i < 62; i++) {//不足长度补0
            srcBinaryStr.insert(0, "0");
        }
        StringBuilder distStr = new StringBuilder();//低位高位重新打散组装
        for (int i = 0; i < 62 / 2; i++) {
            distStr.append(srcBinaryStr.charAt(31 - i - 1));
            distStr.append(srcBinaryStr.charAt(31 + i));
        }
        StringBuilder dist = new StringBuilder(Long.parseLong(distStr.toString(), 2) + "");//结果
        while (dist.length() < 19) {
            dist.insert(0, "0");
        }
        dist.insert(0, busType.getPrefix());
        return dist.toString();
    }

    /**
     * 根据无序码得到id
     */
    public static Long getDisorderId(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        StringBuilder srcBinaryStr = new StringBuilder(Long.toBinaryString(Long.parseLong(StringUtils.stripStart(code.substring(2), "0"))));//二进制字符串
        for (int i = srcBinaryStr.length(); i < 62; i++) {//不足长度补0
            srcBinaryStr.insert(0, "0");
        }
        StringBuilder distStr = new StringBuilder();
        for (int i = 0; i < 62; i++) {
            if (i % 2 == 0) {
                distStr.insert(0, srcBinaryStr.charAt(i));
            } else {
                distStr.append(srcBinaryStr.charAt(i));
            }
        }
        return Long.parseLong(StringUtils.stripStart(distStr.toString(), "0"), 2);
    }

    public static String encryptMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        mobile=mobile.trim();
        if (mobile.length() < 7) {
            return null;
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(7);
    }
}
