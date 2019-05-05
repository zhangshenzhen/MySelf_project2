package com.shenhen.http;

public class TestHttp {
    public static <T, M> void sendJsonRequest(String url, T reauestdata, Class<M> request, JsonDataListener listener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallBackListenet callBackListenet = new JsonCallBackListener<>(request, listener);
        HttpTask httpTask = new HttpTask(url, reauestdata, httpRequest, callBackListenet);
        ThreadPoolManager.getInstance().adTask(httpTask);
    }


}
