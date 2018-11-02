package com.drc.tools.Crypto;

import android.util.Log;

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

    public static String generateKey() {
        try {
            SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
            byte[] bytes_key = new byte[20];
            localSecureRandom.nextBytes(bytes_key);
            String str_key = toHexString(bytes_key);
            return str_key;
        } catch (Exception e) {
            Log.e("drc", "generateKey: error");
        }
        return null;
    }

    public static byte[] generateSalt(int length) {
        byte[] salt = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            salt = new byte[length];
            sr.nextBytes(salt);
        } catch (Exception ex) {
            Log.e("drc", "generateSalt: error");
        }
        return salt;
    }

    public static byte[] ReadSalt(String saltStr) {

        String hexString = saltStr.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static String SaveSalt(byte[] salt) {
        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < salt.length; i++) {
            if ((salt[i] & 0xff) < 0x10)
                //0~F前面补零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & salt[i]));
        }
        return hexString.toString().toLowerCase();
    }

    public static byte[] getRawKey(String password, byte[] salt) {
        SecretKey key = null;
        try {
            int iterationCount = 1000;
            int keyLength = 256;
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBK_DF2_HmacSHA1);
            byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
            key = new SecretKeySpec(keyBytes, AES);
        } catch (Exception ex) {
        }
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
        }
        return null;
    }

    public static String toHexString(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            addHexString(result, buf[i]);
        }
        return result.toString();
    }

    private static void addHexString(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
