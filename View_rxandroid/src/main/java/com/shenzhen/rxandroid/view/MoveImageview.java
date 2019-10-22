package com.shenzhen.rxandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class MoveImageview extends android.support.v7.widget.AppCompatImageView {
    private String TAG = MoveImageview.class.getName();
    private float mDownX;
    private float mDownY;
    public Scroller mScroller;

    public MoveImageview(Context context) {
        this(context, null);
    }

    public MoveImageview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            /**
             * 点击事件传到View时，记下触摸点的坐标
             */
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                Log.d(TAG, "ACTION_DOWN : getX :" + mDownX + "  , getY : " + mDownY);
                break;
            /**
             * 手指移动时记下移动后触摸的坐标并计算出偏移量，
             * 通过偏移量修改View的坐标。
             */
            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) (event.getX() - mDownX);
                int offsetY = (int) (event.getY() - mDownY);
                Log.d(TAG, "ACTION_Move: offsetX :" + offsetX + "  , offsetY : " + offsetY);
                Log.d(TAG, "ACTION_Move: event.getX() :" + event.getX() + "  , getY : " + event.getY());
                //方法一 通过layout()实现View的滑动
                /*layout(getLeft() + offsetX, getTop() + offsetY,
                        getRight() + offsetX, getBottom() + offsetY);*/


                // 方法2.通过offsetLeftAndRight()和offsetTopAndBottom()实现View的滑动
              /*offsetLeftAndRight(offsetX);
                offsetTopAndBottom(offsetY);*/

                //方法 3.通过LayoutParams实现View的滑动  wodn 事件出现view偏移
             /*  LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                layoutParams.leftMargin = getLeft() + offsetX;
                layoutParams.topMargin = getTop() + offsetY;
                setLayoutParams(layoutParams);*/

                // 方法4.通过scrollBy()实现View的滑动
                /* 通过scrollBy()实现View的滑动
                 * scrollTo(x,y)表示移动到一个具体的坐标点，scrollBy(dx,dy)表示移动的增量dx和dy，
                 * scrollBy()最终也要调用scrollTo()。
                 * scrollTo()和scrollBy()都是移动View的内容，如果在ViewGroup中使用，
                 * 则移动的是其所有的子View。*/
               // ((View)getParent()).scrollBy(-offsetX, -offsetY);
                scrollTo((int)event.getX(),(int)event.getY());
                break;
           /* case MotionEvent.ACTION_UP:
                smoothScrollTo((int) event.getX(), (int) event.getY());
                break;*/
            default:
                break;
        }
        return true;
    }


    /**
     * 方法 5
     * 重写computeScroll()方法
     * 系统会在绘制View的时候在draw()方法中调用该方法，
     * 在这个方法中，我们调用父类的scrollTo()方法并通过Scroller来不断获取当前的滚动值，
     * 每滑动一小段距离我们就调用invalidate()方法不断的进行重绘，重绘就会调用computeScroll()方法，
     * 这样我们通过不断的移动一个小的距离并连贯起来就实现了平滑移动的效果。
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        // 计算滚动的偏移量
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public void smoothScrollTo(int x, int y) {
        int startX = ((View)getParent()).getScrollX();
        int startY = ((View)getParent()).getScrollY();
        // 开始滚动
        Log.d(TAG, "Chad : x = " + startX + " , y = " + startY);
        mScroller.startScroll(startX, startY, -x, -y, 0);
        invalidate();
    }
}
