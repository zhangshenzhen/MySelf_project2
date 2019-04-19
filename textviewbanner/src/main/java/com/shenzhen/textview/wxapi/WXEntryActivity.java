package com.shenzhen.textview.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.shenzhen.textview.MyApplication;
import com.shenzhen.textview.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    // IWXAPI 是第三方APP和微信通信的openapi接口
    private static final String TAG = "WXEntryActivity";
    private static final int RETURN_MSG_TYPE_LOGIN = 1; //登录
    private static final int RETURN_MSG_TYPE_SHARE = 2; //分享
    private static final int REQUEST_WX_TOKEN = 123456;
    private static final int REQUEST_USER_INFO = 456124;
    private static final int REQUEST_AUTHORIZE = 4564545;
    private static final int REQUEST_INTEGRAL = 784761461;
    private Context mContext;
    private String openid;
    private String nickname;
    private String headUrl;
    private String unionid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //如果没回调onResp，估计是这句没有写
        MyApplication.mWxApi.handleIntent(getIntent(), this);

//        String appId = "wxd930ea5d5a258f4f"; // 填应用AppId
//        IWXAPI api = WXAPIFactory.createWXAPI(context, appId);

    }

    // ΢微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        Toast.makeText(this, "openid = " + req.openId +"  " + req.getType(), Toast.LENGTH_SHORT).show();

        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//			goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//			goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
               // Toast.makeText(this, R.string.launch_from_wx, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        Log.i(TAG, "onResp:------>");
        Log.i(TAG, "error_code:---->" + resp.errCode);


        }
 }



    /**
     * 请求token
     *
     * @param code
     */






