package com.drc.tools.Crypto;

import android.util.Log;

public class TestCrypto {
    public static final void Test() {
        int testMaxCount = 1;//测试的最大数据条数
        //添加测试数据
        for (int i = 0; i < testMaxCount; i++) {
            //生成一个动态key
            String key = "red01";
            Log.i("drc", "Key  -> " + key);

            byte[] salt = AesUtils.generateSalt(32);

            byte[] raw = AesUtils.getRawKey(key, salt);

            String jsonData = String.format("No:%s ;Name:%s", i, i) + ":";
            Log.w("drc", "加密前 -> " + jsonData);

            String encryStr = AesUtils.encrypt(raw, jsonData);
            Log.v("drc", "加密后 -> " + encryStr);

            String saltstr = AesUtils.SaveSalt(salt);
            byte[] salt2 = AesUtils.ReadSalt(saltstr);
            Log.i("drc", "salt -> " + AesUtils.toHexString(salt));
            Log.i("drc", "salt2-> " + AesUtils.toHexString(salt2));
            byte[] raw2 = AesUtils.getRawKey(key, salt2);
            Log.i("drc", "raw  -> " + AesUtils.toHexString(raw));
            Log.v("drc", "raw2 -> " + AesUtils.toHexString(raw2));

            String decryStr = AesUtils.decrypt(raw2, encryStr);
            Log.w("drc", "解密后 -> " + decryStr);
            Log.w("drc", " " );
        }
    }
}
