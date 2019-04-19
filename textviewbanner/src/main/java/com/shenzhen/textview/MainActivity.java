package com.shenzhen.textview;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paradoxie.autoscrolltextview.VerticalTextview;
import com.shenzhen.textview.utils.Format;
import com.shenzhen.textview.utils.StatusBarUtil;
import com.shenzhen.textview.view.ITextBannerItemClickListener;
import com.shenzhen.textview.view.TextBannerView;
import com.shenzhen.textview.wxapi.WXHelper;
import com.tencent.mm.opensdk.modelpay.PayReq;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextBannerView mTvBanner;
    private TextBannerView mTvBanner1;
    private TextBannerView mTvBanner2;
    private TextBannerView mTvBanner3;
    private TextBannerView mTvBanner4;
    private List<String> mList;
    private Toolbar toolbar;
    private int num = 0;
    private boolean isWx = false;
    private VerticalTextview textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.immersive(this);
        initView();
        initData();
        setListener();

    }


    private void initView() {
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toobar);
        //把布局中的Toolbar当作ActionBar
        setSupportActionBar(toolbar);    //必须把主题设置会为 NoActionbar
        //设置图标
        toolbar.setLogo(R.mipmap.ic_launcher);
        //设置标题 二选一
        getSupportActionBar().setTitle("shenzhen");
        toolbar.setTitle("zhangshenzhen");
        //标题颜色
        toolbar.setTitleTextColor(Color.parseColor("#ff0000"));
        //设置副标题
        toolbar.setSubtitle(Format.fomartdate(System.currentTimeMillis() + ""));
        //设置返回键 二选一
        //返回按钮
        // toolbar.setNavigationIcon(R.mipmap.back);*/

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        /*
         *
         *  setSupportActionBar(tlTitle);//设置toolbar
         *  ctlTitle.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
         *  ctlTitle.setExpandedTitleGravity(Gravity.CENTER);////设置展开后标题的位置
         *  ctlTitle.setExpandedTitleColor(Color.WHITE);//设置展开后标题的颜色
         *  ctlTitle.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后标题的颜色
         */
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置标题 二选一
        //getSupportActionBar().setTitle("shenzhen");
        //toolbar.setTitle("zhangshenzhen");

        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);
        collapsingToolbar.setExpandedTitleGravity(Gravity.LEFT | Gravity.BOTTOM);
        // collapsingToolbar.setTitle(fruitName);
        AppBarLayout appbar = findViewById(R.id.appbar);
        final FloatingActionButton FloatingActionButton = findViewById(R.id.FloatingActionButton);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("移动", ":" + verticalOffset);                                                                                          //8.0f 修改简便灵敏度
                toolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.colorPrimary), Math.abs(verticalOffset * 1.0f) / (8.0f * appBarLayout.getTotalScrollRange())));
                toolbar.setTitleTextColor(changeAlpha(getResources().getColor(R.color.colorBlack), Math.abs(verticalOffset * 1.0f) / (1.0f * appBarLayout.getTotalScrollRange())));

                //动态修改悬浮按钮大小
                //FloatingActionButton.setCustomSize(FloatingActionButton.getSize()*verticalOffset/appBarLayout.getTotalScrollRange());
            }
        });

        FloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "调用了 "+!isWx, Toast.LENGTH_SHORT).show();
                if (!isWx) {
                    isWx = !isWx;
                    StartPayFor startPayFor = new StartPayFor(MainActivity.this);
                    startPayFor.startAlipay();
                 } else {
                    try {
                        isWx = !isWx;
                        weixin();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        mTvBanner = findViewById(R.id.tv_banner);
        mTvBanner1 = findViewById(R.id.tv_banner1);
        mTvBanner2 = findViewById(R.id.tv_banner2);
        mTvBanner3 = findViewById(R.id.tv_banner3);
        mTvBanner4 = findViewById(R.id.tv_banner4);
        资源框架();
    }

    private void 资源框架() {
        textView = (VerticalTextview) findViewById(R.id.text);
        final ArrayList<String> titleList = new ArrayList<String>();
        titleList.add("你是天上最受宠的一架钢琴");
        titleList.add("我是丑人脸上的鼻涕");
        titleList.add("你发出完美的声音");
        titleList.add("我被默默揩去");
        titleList.add("你冷酷外表下藏着诗情画意");
        titleList.add("我已经够胖还吃东西");
        titleList.add("你踏着七彩祥云离去");
        titleList.add("我被留在这里");
        textView.setTextList(titleList);
        textView.setText(18, 0, Color.WHITE);//设置属性
        textView.setTextStillTime(3000);//设置停留时长间隔
        textView.setAnimTime(500);//设置进入和退出的时间间隔
        textView.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "点击了 : " + titleList.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        textView.stopAutoScroll();
    }


    private void weixin() throws JSONException {
        //{"resultStatus":0,"msg":"SUCCESS","resultData":{"content":"{\"appid\":\"wx2ed6ff9d4a4f89a9\",\"noncestr\":\"UWBAVtZdqrEWaICE\",\"package\":\"Sign=WXPay\",\"partnerid\":\"1501213791\",\"prepayid\":\"wx1809355094899803e46bd1c41127181708\",\"sign\":\"00E77DD1A64FB96BE71D306697DDBB48\",\"timestamp\":1555551350}"}}
        String content = "{\"appid\":\"wx2ed6ff9d4a4f89a9\",\"noncestr\":\"UWBAVtZdqrEWaICE\",\"package\":\"Sign=WXPay\",\"partnerid\":\"1501213791\",\"prepayid\":\"wx1809355094899803e46bd1c41127181708\",\"sign\":\"00E77DD1A64FB96BE71D306697DDBB48\",\"timestamp\":1555551350}";
        JSONObject json = new JSONObject(content);
        WXHelper wxHelper = new WXHelper(this, json.getString("appid"));
        PayReq req = new PayReq();
        req.appId = json.getString("appid");
        req.partnerId = json.getString("partnerid");
        req.prepayId = json.getString("prepayid");
        req.nonceStr = json.getString("noncestr");
        req.timeStamp = json.getString("timestamp");
        req.packageValue = json.getString("package");
        req.sign = json.getString("sign");
        req.extData = "success";
        wxHelper.pay(req);
    }


    /**
     * 根据百分比改变颜色透明度
     */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }


    private void initData() {
        mList = new ArrayList<>();
        mList.add("学好Java、Android、C++、html+css+js");
        mList.add("走遍天下都不怕！！！！！");
        mList.add("不是我吹，就怕你做不到，哈哈");
        mList.add("superluo");
        mList.add("你是最棒的，奔跑吧孩子！");
        /**
         * 设置数据，方式一
         */
        mTvBanner.setDatas(mList);
        mTvBanner.setDatas(mList);
        mTvBanner1.setDatas(mList);
        mTvBanner2.setDatas(mList);
        mTvBanner3.setDatas(mList);


        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        /**
         * 设置数据（带图标的数据），方式二
         */
        //第一个参数：数据 。第二参数：drawable.  第三参数drawable尺寸。第四参数图标位置
        mTvBanner4.setDatasWithDrawableIcon(mList, drawable, 18, Gravity.LEFT);

    }

    private void setListener() {
        mTvBanner.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position) + ">>" + data, Toast.LENGTH_SHORT).show();
            }
        });

        mTvBanner1.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position) + ">>" + data, Toast.LENGTH_SHORT).show();
            }
        });

        mTvBanner2.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position) + ">>" + data, Toast.LENGTH_SHORT).show();
            }
        });

        mTvBanner3.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position) + ">>" + data, Toast.LENGTH_SHORT).show();
            }
        });

        mTvBanner4.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position) + ">>" + data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**调用startViewAnimator()重新开始文字轮播*/
        mTvBanner.startViewAnimator();
        mTvBanner1.startViewAnimator();
        mTvBanner2.startViewAnimator();
        mTvBanner3.startViewAnimator();
        mTvBanner4.startViewAnimator();
        textView.startAutoScroll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        /**调用stopViewAnimator()暂停文字轮播，避免文字重影*/
        mTvBanner.stopViewAnimator();
        mTvBanner1.stopViewAnimator();
        mTvBanner2.stopViewAnimator();
        mTvBanner3.stopViewAnimator();
        mTvBanner4.stopViewAnimator();
    }
    /*
     * {"resultStatus":0,"msg":"SUCCESS","resultData":{"content":"{\"appid\":\"wx2ed6ff9d4a4f89a9\",\"noncestr\":\"wfA8PP8xC6EpqDv5\",\"package\":\"Sign=WXPay\",\"partnerid\":\"1501213791\",\"prepayid\":\"wx171558282285054a672ba6c20967197465\",\"sign\":\"F57AF4E6197DBB99BFDC83014989F32A\",\"timestamp\":1555487908}"}}*/
}
