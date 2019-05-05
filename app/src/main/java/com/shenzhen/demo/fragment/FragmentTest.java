package com.shenzhen.demo.fragment;


import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.provider.ContactsContract;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.shenzhen.demo.R;
import com.shenzhen.demo.base.MyBaseFragment;
import com.shenzhen.demo.widget.XCollapsingToolbarLayout;


import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.Callable;

import butterknife.BindView;

public class FragmentTest extends MyBaseFragment implements XCollapsingToolbarLayout.OnScrimsListener {

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

    public static FragmentTest newInstance() {
        return new FragmentTest();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_test;
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
     currentTime();
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
        Log.i("shown=",""+shown);
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
     /*
     * */
    private void currentTime() {
        Log.i(" : shown= >>> ", Thread.currentThread().getName());

        //获取网络时间
        new Thread(){
            @Override
            public void run() {
                try {
                 getNetWorkTime();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
  /*说明激励的作用：

  *
  * */
    /*
    * 获取当前的网络时间
    * */
    private long getNetWorkTime() throws IOException {
        URL url=new URL("http://www.baidu.com") ;
        URLConnection conn = url.openConnection();
        conn.connect();
        long dateL=conn.getDate();
        Date date=new Date(dateL);
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        Log.i(dateL+" : shown=", Thread.currentThread().getName()+""+df.format(date) );
       return dateL;
    }

   /*
   * aaDBb
   * BAbAB
   * DBCcC       +10
   *
   * ABC         +6
   * ABCE
   * ACDE    abcd
   * ABCD    cde
   * BCDE
   *
   * X          +12
   * v
   * X
   * V      x
   * V
   *
   *
   * */
}

