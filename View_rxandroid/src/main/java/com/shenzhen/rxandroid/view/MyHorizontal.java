package com.shenzhen.rxandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MyHorizontal extends ViewGroup {
    public MyHorizontal(Context context) {
        this(context,null);
    }

    public MyHorizontal(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyHorizontal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    /**
     * 以下三个函数需要重写
     * 根据不同参数创建容器自身的布局参数对象并返回
     */

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
         if(p instanceof LayoutParams){
             return new LayoutParams((LayoutParams)p);
         }else if(p instanceof MarginLayoutParams){
             return new MarginLayoutParams((MarginLayoutParams)p);
         }
        return new LayoutParams(p);
    }

    /**
     * 添加容器自身的布局参数对象
     * 继承自ViewGroup.MarginLayoutParams
     */

    public static class Layoutparms extends MarginLayoutParams{

        public Layoutparms(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public Layoutparms(int width, int height) {
            super(width, height);
        }

        public Layoutparms(MarginLayoutParams source) {
            super(source);
        }
    }
}
