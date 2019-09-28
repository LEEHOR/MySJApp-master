package com.shenjing.dengyuejinfu.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * author : Leehor
 * date   : 2019/8/2918:18
 * version: 1.0
 * desc   :map和javabean互相转化
 */
public class FileUtils {

    /**
     * 读取内容保存本地
     * @param body
     * @param file
     * @return
     */
    public static  boolean writeResponseBodyToDisk(ResponseBody body, File file) {
        try {

            //初始化输入流
            InputStream inputStream = null;
            //初始化输出流
            OutputStream outputStream = null;
            try {
                //设置每次读写的字节
                byte[] fileReader = new byte[4096];
                //long fileSize = body.contentLength();
                //请求返回的字节流
                inputStream = body.byteStream();
                //创建输出流
                outputStream = new FileOutputStream(file);
                //进行读取操作
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    //进行写入操作
                    outputStream.write(fileReader, 0, read);
                }
                //刷新
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    //关闭输入流
                    inputStream.close();
                }
                if (outputStream != null) {
                    //关闭输出流
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}
