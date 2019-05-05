package com.shenhen.http;

public interface IHttpRequest {
    void seturl(String url);

    void setData(byte[] bytes);

    void setListener(CallBackListenet backListenet);

    void execuate();
}
