package com.sdj.dragphotoview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.davemorrissey.labs.subscaleview.FingerPanGroup;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView3;

/**
 * Created by sdj on 2017/11/1.
 */

public class ByteDanceActivity extends AppCompatActivity {
   private SubsamplingScaleImageView3 subsamplingScaleImageView3;
    private FingerPanGroup fingerPanGroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_byte_dance);
        subsamplingScaleImageView3 = findViewById(R.id.icon);
        ImageSource imageSource = ImageSource.resource(R.mipmap.too_long);
        subsamplingScaleImageView3.setImage(imageSource);
        subsamplingScaleImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        fingerPanGroup = findViewById(R.id.fingerGroup);
        fingerPanGroup.setOnAlphaChangeListener(new FingerPanGroup.onAlphaChangedListener() {
            @Override
            public void onAlphaChanged(float alpha) {
                //更改透明度
            }

            @Override
            public void onTranslationYChanged(float translationY) {
                //根据距离显示隐藏主UI控件
                if(Math.abs(translationY) > 0){
                    //todo

                }else {

                }
            }
        });

    }
    /*
    * 1、项目采用灵活显示和隐藏功能，后台服务端进项配置来控制开放的与功能;
      2、首页采用自定义广告轮播和RecycleView格局显示,通过自定义dialog 弹出列表优惠券滑和加载透明
       的WebView;
      3、铺子界面通过二级菜单下的筛选条件下选择商家进行消费,包括微信/支付宝、平台支付、接入易宝付等
      充值和消费;
      4、网络部分采用Volley框架进行二次封装与服务端交互,
      5、众筹主页整采用CoordinatorLayout布局，继承线性布局实现轮播广告功能,
      6、包含WebView与安卓原生代码之间的互相跳转参数传递;
      7、项目钱包中带有绑定银行卡、充值以及提现等功能;
      8、多出采用EventBus 通信机制，整合了项目热修复功能;
      9、项目集成友盟、高德地图定位、Z-Xing等功能,
    *
    *
    *
    * */
}
