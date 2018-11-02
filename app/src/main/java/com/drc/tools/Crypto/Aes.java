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

public class Aes {
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = KEY_LENGTH / 8;
    private static final String PBK_DF2_HmacSHA1 = "PBKDF2WithHmacSHA1";
    //AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5PADDING";
    private static final String AES = "AES";
    private static final String SHA1PRNG = "SHA1PRNG";

    public static String generateKey() {
        String str_key = null;
        try {
            SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
            byte[] bytes_key = new byte[SALT_LENGTH];
            localSecureRandom.nextBytes(bytes_key);
            str_key = toHexString(bytes_key);
        } catch (Exception ex) {
            Log.e("drc", "generateKey error: " + ex.toString());
        }
        return str_key;
    }

    public static byte[] generateSalt(int length) {
        byte[] salt = null;
        try {
            SecureRandom sr = SecureRandom.getInstance(SHA1PRNG);
            salt = new byte[length];
            sr.nextBytes(salt);
        } catch (Exception ex) {
            Log.e("drc", "generateSalt error: " + ex.toString());
        }
        return salt;
    }

    public static byte[] fromHexString(String saltStr) {

        String hexString = saltStr.toUpperCase();
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

    public static String toHexString(byte[] salt) {
        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < salt.length; i++) {
            if ((salt[i] & 0xff) < 0x10) hexString.append("0");  //0~F前面补零
            hexString.append(Integer.toHexString(0xFF & salt[i]));
        }
        return hexString.toString().toUpperCase();
    }

    public static byte[] getRawKey(String password, byte[] salt) {
        SecretKey key = null;
        try {
            int iterationCount = 1000;
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, KEY_LENGTH);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBK_DF2_HmacSHA1);
            byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
            key = new SecretKeySpec(keyBytes, AES);
        } catch (Exception ex) {
            Log.e("drc", "getRawKey error: " + ex.toString());
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
        } catch (Exception ex) {
            Log.e("drc", "encrypt error: " + ex.toString());
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
            Log.e("drc", "decrypt error:" + e.toString());
        }
        return null;
    }
}
