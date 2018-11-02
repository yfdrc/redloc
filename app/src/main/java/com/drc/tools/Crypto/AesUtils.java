package com.drc.tools.Crypto;

import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtils {
    private final static String HEX = "0123456789ABCDEF";
    private static final String PBK_DF2_HmacSHA1 = "PBKDF2WithHmacSHA1";        //AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5PADDING";    //AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private static final String AES = "AES";                                        //AES 加密
    private static final String SHA1PRNG = "SHA1PRNG";                            // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法

    /*
     * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
     */
    public static String generateKey() {
        try {
            SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
            byte[] bytes_key = new byte[20];
            localSecureRandom.nextBytes(bytes_key);
            String str_key = toHex(bytes_key);
            return str_key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getSalt(int length) {
        byte[] salt = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            salt = new byte[length];
            sr.nextBytes(salt);
        } catch (Exception ex) {
        }
        return salt;
    }

    // 对密钥进行处理
    public static byte[] getRawKey(String password) {
        SecretKey key=null;
        try {
            /* Store these things on disk used to derive key later: */
            int iterationCount = 1000;
            int keyLength = 256; // 256-bits for AES-256, 128-bits for AES-128, etc
            int saltLength = 32; // bytes; should be the same size as the output (256 / 8 = 32)
            byte[] salt = getSalt(saltLength); // Should be of saltLength

            /* Use this to derive the key from the password: */
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt,
                    iterationCount, keyLength);
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance(PBK_DF2_HmacSHA1);
            byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
            key = new SecretKeySpec(keyBytes, AES);
        }catch (Exception ex){}
        return key.getEncoded();
    }

    public static String encrypt(byte[] rawKey, String cleartext) {
        if (cleartext == "") {
            return cleartext;
        }
        try {
            byte[] clear = cleartext.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(rawKey, AES);
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] result = cipher.doFinal(clear);
            return Base64Encoder.encode(result);
        } catch (Exception e) {
            Log.e("drc", "encrypt: error");
            ;
        }
        return null;
    }

    public static String decrypt(byte[] rawKey, String encrypted) {
        if (encrypted == "") {
            return encrypted;
        }
        try {
            byte[] enc = Base64Decoder.decodeToBytes(encrypted);
            SecretKeySpec skeySpec = new SecretKeySpec(rawKey, AES);
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] result = cipher.doFinal(enc);
            return new String(result);
        } catch (Exception e) {
            Log.e("drc", "decrypt:" + e.toString());
            ;
        }
        return null;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
