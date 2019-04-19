package com.shenzhen.textview.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.shenzhen.textview.MyApplication;
import com.shenzhen.textview.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
		//如果没回调onResp，估计是这句没有写
		MyApplication.mWxApi.handleIntent(getIntent(), this);

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.e("GGGG", "onPayFinish, errCode = " + resp.errCode);
		Log.e("GGGG", "onPayFinish, errCode = " + resp.openId+" "
		+resp.transaction+" "+resp.toString());

		//resp.errCode == 0
		//resp.errCode == -2时为支付取消
		//resp.errCode == -1时为签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
		if(resp.errCode == 0){ //支付成功;
			if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {



				finish();


			/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
			builder.show();*/
			}
		}else if(resp.errCode == -2){

			Toast.makeText(this,"支付取消",Toast.LENGTH_LONG).show();
			finish();
		}else if(resp.errCode == -1){
			Toast.makeText(this,"微信支付异常:"+resp.errStr, Toast.LENGTH_LONG).show();
			finish();

		}
	}


	/*private void processPayResult(int resultCode){
		EventBusMessageEvent event = new EventBusMessageEvent();
		event.setWhat(EventBusWhat.WxPayResult);
		event.setData(resultCode);
		EventBus.getDefault().post(event);
		finish();
	}*/


}