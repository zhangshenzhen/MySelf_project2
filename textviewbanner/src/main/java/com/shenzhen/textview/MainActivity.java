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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenzhen.textview.utils.Format;
import com.shenzhen.textview.utils.StatusBarUtil;
import com.shenzhen.textview.view.ITextBannerItemClickListener;
import com.shenzhen.textview.view.TextBannerView;


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
        //要使用setSupportActionBar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // collapsingToolbar.setTitle(fruitName);
        AppBarLayout appbar = findViewById(R.id.appbar);
        final FloatingActionButton FloatingActionButton = findViewById(R.id.FloatingActionButton);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("移动",":"+verticalOffset);                                                                                          //8.0f 修改简便灵敏度
                toolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.colorPrimary), Math.abs(verticalOffset * 1.0f) /(8.0f* appBarLayout.getTotalScrollRange())));
                toolbar.setTitleTextColor(changeAlpha(getResources().getColor(R.color.colorBlack), Math.abs(verticalOffset * 1.0f) /(1.0f* appBarLayout.getTotalScrollRange())));

                //动态修改悬浮按钮大小
                //FloatingActionButton.setCustomSize(FloatingActionButton.getSize()*verticalOffset/appBarLayout.getTotalScrollRange());
            }
        });

        mTvBanner = findViewById(R.id.tv_banner);
        mTvBanner1 = findViewById(R.id.tv_banner1);
        mTvBanner2 = findViewById(R.id.tv_banner2);
        mTvBanner3 = findViewById(R.id.tv_banner3);
        mTvBanner4 = findViewById(R.id.tv_banner4);
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
        mList.add("学好Java、Android、C#、C、ios、html+css+js");
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

}
