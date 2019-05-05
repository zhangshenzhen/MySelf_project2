package com.shenhen.http;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;

public class JsonHttpRequest implements IHttpRequest {
    private String url;
    private byte[] data;//数据缓冲区
    private CallBackListenet backListenet;
    private HttpURLConnection httpURLConnection;
    private HttpURLConnection connection;

    public JsonHttpRequest() {

    }

    @Override
    public void seturl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] bytes) {
        this.data = bytes;
    }

    @Override
    public void setListener(CallBackListenet backListenet) {
        this.backListenet = backListenet;
    }

    @Override
    public void execuate() {
        //执行具体的连接
        try {
            URL url = new URL(this.url);
            // 连接类的父类，抽象类
            // http的连接类
            httpURLConnection = (HttpURLConnection)url.openConnection();
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
            //------------------------------------  //获得字节流
//            OutputStream outp = httpURLConnection.getOutputStream();
//            BufferedOutputStream bos = new BufferedOutputStream(outp);
//            bos.write(new byte[128]);//将字节流写入数据缓冲区
//            bos.flush();
//            outp.close();
//            bos.close();
            //-------------- 字节流写入数据
            Log.d("shenhen --->> "," getResponseCode :"+httpURLConnection.getResponseCode());
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                backListenet.onSucess(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            backListenet.onFailar(e.getMessage());
        }finally {
            httpURLConnection.disconnect();
        }



    }
}
