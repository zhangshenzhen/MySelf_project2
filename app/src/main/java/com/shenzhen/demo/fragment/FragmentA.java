package com.shenzhen.demo.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.shenzhen.demo.R;
import com.shenzhen.demo.activity.HomeActivity;
import com.shenzhen.demo.base.MyBaseFragment;
import com.shenzhen.demo.widget.XCollapsingToolbarLayout;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;

public class FragmentA  extends MyBaseFragment implements XCollapsingToolbarLayout.OnScrimsListener {

    @BindView(R.id.abl_test_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.ctl_test_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.t_test_title)
    Toolbar mToolbar;
    @BindView(R.id.tb_test_a_bar)
    TitleBar mTitleBar;

    @BindView(R.id.tv_test_address)
    TextView mAddressView;
    @BindView(R.id.tv_test_search)
    TextView mSearchView;

    public static FragmentA newInstance() {
        return new FragmentA();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_a;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        // 给这个ToolBar设置顶部内边距，才能和TitleBar进行对齐
        ImmersionBar.setTitleBar(getFragmentActivity(), mToolbar);

        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return mCollapsingToolbarLayout.isScrimsShown();
    }

    /**
     * {@link XCollapsingToolbarLayout.OnScrimsListener}
     */
    @Override
    public void onScrimsStateChange(boolean shown) {
        // CollapsingToolbarLayout 发生了渐变
        if (shown) {
            mAddressView.setTextColor(getResources().getColor(R.color.black));
            mSearchView.setBackgroundResource(R.drawable.bg_home_search_bar_gray);
            getStatusBarConfig().statusBarDarkFont(true).init();
        }else {
            mAddressView.setTextColor(getResources().getColor(R.color.white));
            mSearchView.setBackgroundResource(R.drawable.bg_home_search_bar_transparent);
            getStatusBarConfig().statusBarDarkFont(false).init();
        }
    }

    //@TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("gps", "版本兼容Context ：" + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            onAttachToContext(context);
        }
    }

   // @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("gps", "版本兼容activity ：" + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
           onAttachToContext(activity);
        }
    }

    private void onAttachToContext(Context context){
        HomeActivity mainActivity = (HomeActivity) context;
         Map map = new Hashtable();


         long lasttime = Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10));
         Log.i("gps", System.currentTimeMillis()+"版本兼容调用了  : "+lasttime );
    }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}
