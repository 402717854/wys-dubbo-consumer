package com.wys.dubbo.consumer.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;


/**
 * @Description: 密码加密
 */
public class AesUtil {

    /**
     * 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同
     */
    private static String KEY = "uuC201807-17Cent";

    private static String IV = "UuC20180717Cente";

    /**
     * 加密方法
     *
     * @param data 要加密的数据
     * @param key  加密key
     * @param iv   加密iv
     * @return 加密的结果
     */
    public static String encrypt(String data, String key, String iv) {
        try {
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
          }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new Base64().encodeToString(encrypted);

      } catch (Exception e) {
            return null;
      }
  }

    /**
     * 解密方法
     *
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv   解密iv
     * @return 解密的结果
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = new Base64().decode(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8").trim();
            return originalString;
      } catch (Exception e) {
            return null;
      }
  }

    /**
     * 使用默认的key和iv加密
     */
    public static String encrypt(String data) {
        return encrypt(data, KEY, IV);
  }

    /**
     * 使用默认的key和iv解密
     */
    public static String desEncrypt(String data) throws Exception {
        return desEncrypt(data, KEY, IV);
  }

    public static String getSHA1(String str) {
        try {

            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
              }
                hexstr.append(shaHex);
          }
            return hexstr.toString();
      } catch (Exception e) {
            throw new RuntimeException("sha加密生成签名失败");
      }
  }

    /**
     * aes加密后，转base64编码
     * @param data       需加密的数据
     * @param password   密钥
     * @return
     * @throws Exception
     */
    public static String encryptBase64(String data, String password) throws Exception {
        SecretKeySpec keyspec = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keyspec);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return new String(Base64.encodeBase64(encrypted));
  }

//    public static void main(String[] args) throws Exception {
//        String encryptStr = encrypt("abc123456");
//        //加密后结果为：JNrcSAh7MStR3jQddTMJhg==
//        System.out.println("密文密码："+encryptStr);
//
//        String decryptStr = desEncrypt("JNrcSAh7MStR3jQddTMJhg==");
//        //解密后结果为：abc123456
//        System.out.println("解密后的明文密码："+decryptStr);
//  }
}
