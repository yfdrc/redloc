package com.drc.tools.Crypto;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TestCrypto {
    public static final void Test(){
        int testMaxCount = 1;//测试的最大数据条数
        //添加测试数据
        for (int i = 0; i < testMaxCount; i++) {
            //生成一个动态key
            String secretKey = AesUtils.generateKey();
            byte[] rawKey = null;
            try {
                rawKey = AesUtils.getRawKey(secretKey);
            }catch (Exception ex){
            }
            Log.e("drc", "动态Key -> " + secretKey);
            Log.e("drc", "Keyraw -> " + rawKey.toString());
            Log.e("drc", "salt -> " + AesUtils.getSalt(32).toString());

            //FastJson生成json数据
            String jsonData = String.format("No:%s ;Name:%s",i,i)+":";
            Log.e("drc", "加密前 -> " + jsonData);

            //AES加密
            long start = System.currentTimeMillis();
            String encryStr = AesUtils.encrypt(rawKey, jsonData);
            long end = System.currentTimeMillis();
            Log.e("drc", "加密后 -> " + encryStr);

            //AES解密
            start = System.currentTimeMillis();
            String decryStr = AesUtils.decrypt(rawKey, encryStr);
            end = System.currentTimeMillis();
            Log.e("drc", "解密后 -> " + decryStr);
        }
    }
}
