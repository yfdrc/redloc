package com.drc.tools.FileSystem;

import android.content.Context;
import android.icu.text.SimpleDateFormat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Properties;

import it.sauronsoftware.ftp4j.FTPClient;

public class DrcFilesystem {
    private final static String TAG = "Filesystem";

    private static int mupcs = 0;
    private static String mfilename = "";

    public static String getPkPath(Context context) {
        return "/data/data/" + context.getPackageName() + "/files/";
    }

    public static String getSDPath() {
        return "/sdcard/";
    }

    public static Properties getFSConfigProperties() {
        Properties properties = new Properties();
        InputStream in = DrcFilesystem.class.getResourceAsStream("/assets/App.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void SaveAndSend(final Context context, String nr) {
        mupcs++;
        SimpleDateFormat df = new SimpleDateFormat("dd");
        mfilename = "wz" + df.format(new Date()) + ".txt";
        SaveTextFile(context, mfilename, nr, Context.MODE_APPEND);
        if (mupcs > 12) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = getFSConfigProperties().getProperty("Url");
                    String username = getFSConfigProperties().getProperty("UserName");
                    String password = getFSConfigProperties().getProperty("PassWord");
                    String srcpath = getPkPath(context);
                    String decpath = getFSConfigProperties().getProperty("Dpath");
                    DrcFilesystem.FtpPutFile(url, username, password, srcpath, decpath, mfilename);
                }
            }).start();
            mupcs = 0;
        }
    }

    public static void SaveTextFile(Context context, String filename, String nr, int openMode) {
        //Log.i(TAG, "SaveTextFile: ok");
        FileOutputStream out = null;
        BufferedWriter write = null;
        try {
            out = context.openFileOutput(filename, openMode);
            write = new BufferedWriter(new OutputStreamWriter(out));
            write.write(nr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (write != null) {
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void FtpPutFile(String host, String username, String password, String srcPath, String decPath, String fileName) {
        FTPClient client = new FTPClient();
        File file = null;
        try {
            if (srcPath != "" && !(srcPath.endsWith("/") || srcPath.endsWith("\\"))) {
                srcPath = srcPath + "/";
            }
            file = new File(srcPath + fileName);
            client.connect(host, 21);
            client.login(username, password);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory(decPath);
            client.upload(file);
        } catch (Exception e1) {
            try {
                client.disconnect(true);
            } catch (Exception e2) {
            }
        }
        file = null;
    }
}
