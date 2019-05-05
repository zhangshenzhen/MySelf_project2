package com.shenhen.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;


public class JsonCallBackListener<T> implements CallBackListenet {
    private Class<T> requestClass;
    public Handler mHandler = new Handler(Looper.getMainLooper());//确保在主线程
    private JsonDataListener jsonDataListener;
    public JsonCallBackListener(Class<T> requestClass,JsonDataListener jsonDataListener) {
        this.requestClass = requestClass;
        this.jsonDataListener = jsonDataListener;
    }

    @Override
    public void onSucess(InputStream inputStream) {
        try {
            //将流转化成Response
            String response = InputStreamUtils.ScreamToString(inputStream);
            final T clazz = JSON.parseObject(response,requestClass);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    jsonDataListener.onSucess(clazz);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailar(String message) {
        jsonDataListener.onFailar(message);

    }

}
