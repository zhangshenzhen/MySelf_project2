package com.shenzhen.ios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mumu.dialog.MMLoading;
import com.mumu.dialog.MMToast;

/*
* 如何使用：toast和loading可以写在baseActivity中，
* 而dialog直接在对应activity中使用。
* */

public class BaseActivity extends AppCompatActivity {

    private MMLoading mmLoading;
    private MMToast mmToast;
    private MMLoading.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void showLoading() {

        if (mmLoading == null) {
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage("加载中...")
                    .setCancelable(false)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        }else {

            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage("加载中...")
                    .setCancelable(false)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        }
        mmLoading.show();
    }

    protected void showLoading(String msg) {
        if (mmLoading == null) {
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        }else {
            mmLoading.dismiss();
            MMLoading.Builder builder = new MMLoading.Builder(this)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setCancelOutside(false);
            mmLoading = builder.create();
        }
        mmLoading.show();
    }

    protected void hideLoading() {
        if (mmLoading != null && mmLoading.isShowing()) {
            mmLoading.dismiss();
        }
    }

    protected void showToastSuccess(String msg) {
        if (mmToast == null) {
            MMToast.Builder builder=new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(true);
            mmToast=builder.create();
        }else {
            mmToast.cancel();
            MMToast.Builder builder=new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(true);
            mmToast=builder.create();
        }
        mmToast.show();
    }

    protected void showToastFailure(String msg) {
        if (mmToast == null) {
            MMToast.Builder builder=new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(false);
            mmToast=builder.create();
        }else {
            mmToast.cancel();
            MMToast.Builder builder=new MMToast.Builder(this)
                    .setMessage(msg)
                    .setSuccess(false);
            mmToast=builder.create();
        }
        mmToast.show();
    }


}
