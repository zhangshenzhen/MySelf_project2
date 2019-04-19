package com.shenzhen.textview;

import android.app.Application;

import com.shenzhen.textview.wxapi.Constants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MyApplication extends Application {
    public static IWXAPI mWxApi;
    public static com.tencent.mm.opensdk.openapi.IWXAPI api;
    @Override
    public void onCreate() {
        super.onCreate();

        registToWX();
    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(Constants.APP_ID);

        api =  com.tencent.mm.opensdk.openapi.WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(Constants.APP_ID);
    }
}
