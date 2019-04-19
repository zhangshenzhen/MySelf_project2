package com.shenzhen.textview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

public class StartPayFor {

    // 商户PID
    private static final String PARTNER = "2088221711245766";
    // 商户收款账号
    private static final String SELLER = "minszxfy@minszx.com";
    // 商户私钥，pkcs8格式
    private static String RSA_PRIVATE = "";
    // 支付宝公钥
    private static String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;
    /************************************************************/

    private Handler mHandler;


    private Activity activity;
    //{"resultStatus":0,"msg":"SUCCESS","resultData":{"content":"partner=\"2088031390925235\"&seller_id=\"nanjing@minszx.com\"&out_trade_no=\"TNO20190417181529372249556\"&subject=\"在秦兰兰花卉店消费\"&body=\"消费\"&total_fee=\"0.01\"&notify_url=\"http://47.97.16.198:8084/hybPayService/payService/alipayNotify\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&return_url=\"m.alipay.com\"&sign=\"JlZb7PRQH%2FzHFJaLtWWjv5tQzPpIB7PkqfoanpogCSTqTaUEWzJqmIo4P%2BiB72sRkrMMl6hf5veZMH7oPxxlv8ZGBwukGn9yqgBYMVu9jqeH6MGleOUiymYoDik4E%2FmF%2FTwABZoDGxjNLF9gBwH5jVt8G%2BVL8i8T%2FOiLx1hGtzml6nkvjyEm1R74ss10rqlbIA3o0AfDFNsgC3ZK7v54kPs96KSlEOMLzkzq%2BfFweWsT%2B5h5IQwpi0rJVV89HB%2BTDxc3lcGoZhnfNqSi2zqVTuxQ1eMLO0WolDVXAbTUamnMkbQiB77Yj%2FcTp4%2FHewjOPnVGnn9FxP56RgkWTWmm%2BA%3D%3D\"&sign_type=\"RSA\""}}
    private String content = "partner=\"2088031390925235\"&seller_id=\"nanjing@minszx.com\"&out_trade_no=\"TNO20190417181529372249556\"&subject=\"在秦兰兰花卉店消费\"&body=\"消费\"&total_fee=\"0.01\"&notify_url=\"http://47.97.16.198:8084/hybPayService/payService/alipayNotify\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&return_url=\"m.alipay.com\"&sign=\"JlZb7PRQH%2FzHFJaLtWWjv5tQzPpIB7PkqfoanpogCSTqTaUEWzJqmIo4P%2BiB72sRkrMMl6hf5veZMH7oPxxlv8ZGBwukGn9yqgBYMVu9jqeH6MGleOUiymYoDik4E%2FmF%2FTwABZoDGxjNLF9gBwH5jVt8G%2BVL8i8T%2FOiLx1hGtzml6nkvjyEm1R74ss10rqlbIA3o0AfDFNsgC3ZK7v54kPs96KSlEOMLzkzq%2BfFweWsT%2B5h5IQwpi0rJVV89HB%2BTDxc3lcGoZhnfNqSi2zqVTuxQ1eMLO0WolDVXAbTUamnMkbQiB77Yj%2FcTp4%2FHewjOPnVGnn9FxP56RgkWTWmm%2BA%3D%3D\"&sign_type=\"RSA\"";


    public StartPayFor(Activity activity) {
        this.activity = activity;

    }

    /**
     * 开始支付
     * 1.查询终端设备是否存在支付宝认证账户
     */
    public void startAlipay(){

        Log.e("NewAlipay","checkRunnable");

        Runnable checkRunnable = new Runnable() {

            @Override
            public void run(){
                Log.e("NewAlipay","startAlipay");
                // 构造PayTask 对象
                PayTask payTask = new PayTask((Activity) activity);
                // 调用查询接口，获取查询结果 是否存在支付宝账户
                boolean isExist = payTask.checkAccountIfExist();

                if(!isExist){
                }

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                Log.e("NewAlipay","isExist:"+isExist);
                handler.sendMessage(msg);
            }
        };
        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();
    }

    /**
     * 调用SDK支付
     *
     */
    private void pay(final String content) {
    /*    if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(activity)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                }
                            }).show();
            return;
        }
*/
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) activity);
                // 调用支付接口，获取支付结果  并打开支付对话框
                String result = alipay.pay(content);
                Log.e("NewAlipay", "payresult:"+result);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }



    /**
     * 检查结果
     */
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("NewAlipay", msg.what+"--"+msg.obj);
            switch (msg.what) {
                case SDK_CHECK_FLAG:

                    pay(content);

                    break;
                case SDK_PAY_FLAG:
                    /**
                     * 9000	订单支付成功
                     * 8000	正在处理中
                     * 4000	订单支付失败
                     * 6001	用户中途取消
                     * 6002	网络连接出错
                     * TRADE_SUCCESS  交易成功
                     * TRADE_FAIL  交易失败
                     * TRADE_CANCEL  交易成功
                     */
                     String msgCode = (String) msg.obj;
                      //解析回调码，对比支付情况

                    break;
                    }

            }

    };


}
