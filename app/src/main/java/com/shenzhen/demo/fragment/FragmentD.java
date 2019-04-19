package com.shenzhen.demo.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;


import com.hjq.toast.ToastUtils;
import com.shenzhen.demo.R;
import com.shenzhen.demo.activity.TestActivity;
import com.shenzhen.demo.base.MyBaseFragment;

import butterknife.OnClick;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目界面跳转示例
 */
public class FragmentD extends MyBaseFragment {

    public static FragmentD newInstance() {
        return new FragmentD();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_d;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_d_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_test_dialog, R.id.btn_test_login, R.id.btn_test_register, R.id.btn_test_forget, R.id.btn_test_reset,
            R.id.btn_test_setting, R.id.btn_test_about, R.id.btn_test_browser, R.id.btn_test_pay})
    public void onClick(View v) {
        startActivity(TestActivity.class);

       /* switch (v.getId()) {
            case R.id.btn_test_dialog:
                startActivity(DialogActivity.class);
                break;
            case R.id.btn_test_login:
                startActivity(LoginActivity.class);
                break;
            case R.id.btn_test_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.btn_test_forget:
                startActivity(PasswordForgetActivity.class);
                break;
            case R.id.btn_test_reset:
                startActivity(PasswordResetActivity.class);
                break;
            case R.id.btn_test_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.btn_test_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.btn_test_browser:
                startActivity(WebActivity.class);
                break;
            case R.id.btn_test_pay:
                new MessageDialog.Builder(getFragmentActivity())
                        .setTitle("捐赠") // 标题可以不用填写
                        .setMessage("如果您觉得这个开源项目很棒，希望它能更好地坚持开发下去")
                        .setConfirm("支付宝")
                        .setCancel(null) // 设置 null 表示不显示取消按钮
                        //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(Dialog dialog) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("alipays://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2FFKX04202G4K6AVCF5GIY66%3F_s%3Dweb-other"));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    ToastUtils.show("这个开源项目因为您的支持而能够不断更新、完善，非常感谢您的支持");
                                } catch (Exception e) {
                                    ToastUtils.show("打开支付宝失败");
                                }
                            }

                            @Override
                            public void onCancel(Dialog dialog) {}
                        })
                        .show();
                break;
            default:
                break;
        }*/
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}