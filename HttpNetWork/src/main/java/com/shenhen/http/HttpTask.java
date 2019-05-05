package com.shenhen.http;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

//http 任务的实现类
public class HttpTask<T> implements Runnable{
    private IHttpRequest mhttpRequest;
    // 创建异步任务
   public HttpTask(String url ,T reruestdata,IHttpRequest httpRequest,CallBackListenet backListenet){
       httpRequest.seturl(url);
       httpRequest.setListener(backListenet);
       String content = JSON.toJSONString(reruestdata);
       try {
           httpRequest.setData(content.getBytes("gbk"));
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }
       this.mhttpRequest = httpRequest;
   }


    @Override
    public void run() {
      //调用执行具体的网络连接
        mhttpRequest.execuate();
    }
}
