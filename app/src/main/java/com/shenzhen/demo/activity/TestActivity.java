package com.shenzhen.demo.activity;

import android.view.View;

import com.shenzhen.demo.R;
import com.shenzhen.demo.base.HaHa;
import com.shenzhen.demo.base.MyBaseActivivty;

import org.greenrobot.eventbus.EventBus;

public class TestActivity  extends MyBaseActivivty{
    @Override
    protected int getLayoutId() {
        return R.layout.test;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public void Butoon(View view){
        finish();
        eventBus();
    }

    public void eventBus() {
       // String s = new String();
        HaHa haHa = new HaHa();
        EventBus.getDefault().post(haHa);
    }
}
