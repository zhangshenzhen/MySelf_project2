package com.shenzhen.demo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.shenzhen.demo.R;
import com.shenzhen.demo.base.BaseFragmentAdapter;
import com.shenzhen.demo.base.MyBaseActivivty;
import com.shenzhen.demo.fragment.FragmentA;
import com.shenzhen.demo.fragment.FragmentB;
import com.shenzhen.demo.fragment.FragmentC;
import com.shenzhen.demo.fragment.FragmentD;
import com.shenzhen.demo.fragment.FragmentTest;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public  class HomeActivity extends MyBaseActivivty  implements ViewPager.OnPageChangeListener,
         BottomNavigationView.OnNavigationItemSelectedListener  {
    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;
    private BaseFragmentAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_home;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        mViewPager.addOnPageChangeListener(this);
//        mViewPager.setPageTransformer(true, new ZoomFadePageTransformer());

        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void initData() {
        mPagerAdapter = new BaseFragmentAdapter<>(this);

        mPagerAdapter.addFragment(FragmentA.newInstance());
        mPagerAdapter.addFragment(FragmentB.newInstance());
        mPagerAdapter.addFragment(FragmentC.newInstance());
        mPagerAdapter.addFragment(FragmentD.newInstance());
        mPagerAdapter.addFragment(FragmentTest.newInstance());//测试
        mViewPager.setAdapter(mPagerAdapter);

        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }


     @Override
     public void onPageScrolled(int i, float v, int i1) {

     }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
     public void onPageSelected(int position) {
         switch (position) {
             case 0:
                 mBottomNavigationView.setSelectedItemId(R.id.menu_home);
                 break;
             case 1:
                 mBottomNavigationView.setSelectedItemId(R.id.home_found);
                 break;
             case 2:
                 mBottomNavigationView.setSelectedItemId(R.id.home_message);
                 break;
             case 3:
                 mBottomNavigationView.setSelectedItemId(R.id.home_me);
                 break;
             case 4: //测试
                 mBottomNavigationView.setSelectedItemId(R.id.menu_test);
                 break;
         }
     }

     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
         switch (menuItem.getItemId()) {
             case R.id.menu_home:
                 //mViewPager.setCurrentItem(0);
                 //mViewPager.setCurrentItem(0, false);
                 // 如果切换的是相邻之间的 Item 就显示切换动画，如果不是则不要动画
                 mViewPager.setCurrentItem(0, mViewPager.getCurrentItem() == 1);
                 return true;
             case R.id.home_found:
                 //mViewPager.setCurrentItem(1);
                 //mViewPager.setCurrentItem(1, false);
                 mViewPager.setCurrentItem(1, mViewPager.getCurrentItem() == 0 || mViewPager.getCurrentItem() == 2);
                 return true;
             case R.id.home_message:
                 //mViewPager.setCurrentItem(2);
                 //mViewPager.setCurrentItem(2, false);
                 mViewPager.setCurrentItem(2, mViewPager.getCurrentItem() == 1 || mViewPager.getCurrentItem() == 3);
                 return true;
             case R.id.home_me:
                 //mViewPager.setCurrentItem(3);
                 //mViewPager.setCurrentItem(3, false);
                 mViewPager.setCurrentItem(3, mViewPager.getCurrentItem() == 2);
                 return true;
                 //测试
             case R.id.menu_test:
                 //mViewPager.setCurrentItem(3);
                 //mViewPager.setCurrentItem(3, false);
                 mViewPager.setCurrentItem(4, mViewPager.getCurrentItem() == 1 || mViewPager.getCurrentItem() == 3|| mViewPager.getCurrentItem() == 2);
                 return true;
         }
         return false;
     }


    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }

    @Override
    public boolean isSupportSwipeBack() {
        // 不使用侧滑功能
        return !super.isSupportSwipeBack();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String s) {
        Log.d( "home结束了", "..."+Toast.LENGTH_SHORT);
        finish();
    }

 }
