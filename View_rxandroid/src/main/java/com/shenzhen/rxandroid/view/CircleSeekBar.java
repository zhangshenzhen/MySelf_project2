package com.shenzhen.rxandroid.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.shenzhen.rxandroid.R;

public class CircleSeekBar extends View {
    // 进度条画笔
    private Paint mProgressPaint;
    // 滑块画笔
    private Paint mKnobPaint;

    // 进度条可绘制的范围
    private RectF mProgressRectF;

    // 圆形渐变着色器
    private SweepGradient mSweepGradient;
    // 进度条扫过的角度
    private float mSweepAngle;
    // 进度条半径
    private float mRadius;
    // 进度条宽度
    private int mProgressWidth;
    // 滑块宽度 = 直径
    private int mKnobWidth;
    // 可响应Touch事件范围
    private int mTrackArea;
    // 进度条背景色
    private int mBgColor;
    // 滑块颜色
    private int mKnobColor;
    // 当前进度
    private long mProgress;
    // 总进度
    private long mDuration;

    // 是否正在响应Touch事件
    private boolean isDragging;
    public CircleSeekBar(Context context) {
        this(context, null);
    }

    public CircleSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCircleSeekBar(attrs);
    }


    // TODO: 19-10-28
    private void initCircleSeekBar(AttributeSet attrs) {
        if(attrs != null){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleSeekBar);
        mProgressWidth = typedArray.getDimensionPixelSize(R.styleable.CircleSeekBar_progress_width, 6);
        mKnobWidth = typedArray.getDimensionPixelSize(R.styleable.CircleSeekBar_knob_width, mProgressWidth * 2);
        mTrackArea = typedArray.getDimensionPixelSize(R.styleable.CircleSeekBar_track_area, mProgressWidth * 2);
        mBgColor = typedArray.getColor(R.styleable.CircleSeekBar_bg_color, Color.GREEN);
        mKnobColor = typedArray.getColor(R.styleable.CircleSeekBar_knob_color, Color.BLUE);
        mProgress = typedArray.getInt(R.styleable.CircleSeekBar_position, 0);
        mDuration = typedArray.getInt(R.styleable.CircleSeekBar_duration, 0);
        typedArray.recycle();
        }else {
            mProgressWidth = 6;
            mKnobWidth = mProgressWidth * 2;
            mTrackArea = mProgressWidth * 2;
            mBgColor = Color.GREEN;
            mKnobColor = Color.BLUE;
            mProgress = 0;
            mDuration = 0;
        }

        /*
        * Paint.Style.FILL：填充内部
        * Paint.Style.FILL_AND_STROKE  ：填充内部和描边
        * Paint.Style.STROKE  ：描边
        * */

        //创建抗锯齿画笔
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置画笔的样式为描边 (描边就是空心)
        mProgressPaint.setStyle(Paint.Style.STROKE);
        //设置画笔的图像样式为矩形 （前提需设置画笔样式为STROKE或FILL_AND_STROKE）
        mProgressPaint.setStrokeCap(Paint.Cap.SQUARE);

        //创建抗锯齿滑块的画笔
        mKnobPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 设置画笔样式为填充    (描边就是空心)
        mKnobPaint.setStyle(Paint.Style.FILL);
        // 创建进度条可绘制的范围
        mProgressRectF = new RectF();
        // 创建默认圆形渐变着色器
        mSweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2,  Color.YELLOW,Color.RED);
        // 初始化进度条扫过的角度
        mSweepAngle = (float) mProgress / mDuration * 360;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 计算进度条可绘制的范围
        mProgressRectF.top = mProgressWidth / 2 + mTrackArea;
        mProgressRectF.left = mProgressWidth / 2 + mTrackArea;
        mProgressRectF.bottom = h - mProgressWidth / 2 - mTrackArea;
        mProgressRectF.right = w - mProgressWidth / 2 - mTrackArea;

        // 计算进度条的半径
        mRadius = (w - mProgressWidth) / 2 - mTrackArea;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        mProgressPaint.setStrokeWidth(mProgressWidth);
        drawBackground(canvas);//画背景
        drawProgress(canvas);
        drawKnob(canvas);
    }
    /**
     * 绘制进度条背景
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
       //设置画笔的着色器为null
        mProgressPaint.setShader(null);
        mProgressPaint.setColor(mBgColor);
        /**
         * 画圆弧
         * 参数一：确定圆弧形状与尺寸的椭圆边界
         * 参数二：起始角度
         * 参数三：扫过角度
         * 参数四：是否包含圆心
         * 参数五：画笔
         */

        canvas.drawArc(mProgressRectF, 0, 360, false, mProgressPaint);
    }

    /**
     * 绘制进度
     *
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        //创建matrix
        canvas.rotate(0, (float) getWidth() / 2, (float) getHeight() / 2);
         //二者选一
      //  mProgressPaint.setShader(mSweepGradient);
       mProgressPaint.setColor(Color.RED);

        mProgressPaint.setAlpha(255);
        canvas.drawArc(mProgressRectF, 0, mSweepAngle, false, mProgressPaint);
    }


    /**
     * 绘制滑块
     *
     * @param canvas
     */
    private void drawKnob(Canvas canvas) {
        // 根据角度求出弧度
        double radians = Math.toRadians(mSweepAngle);
        // 根据弧度求出滑块圆心x,y坐标
        float centerX = (float) (getWidth() / 2 + mRadius * Math.cos(radians));
        float centerY = (float) (getHeight() / 2 + mRadius * Math.sin(radians));

        mKnobPaint.setColor(mKnobColor);
        canvas.drawCircle(centerX, centerY, (float) mKnobWidth / 2, mKnobPaint);
    }

    public void setProgress(long progress){
        // 当响应Touch事件时，不接收外部传进来的进度
        if (isDragging) {
            return;
        }

        mProgress = progress;

        mSweepAngle = (float) progress / mDuration * 360;

        postInvalidate();
   }
    public void setMax(long max) {
        mDuration = max;
    }
    public void setSweepGradient(int startColor, int endColor) {
        mSweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2, startColor, endColor);
    }
    public void setSweepGradient(SweepGradient gradient) {
        mSweepGradient = gradient;
    }
    public void setProgressWidth(int width) {
        mProgressWidth = dp2px(width);
        invalidate();
    }
    public void setKnobWidth(int width) {
        mKnobWidth = width;
        invalidate();
    }
    public void setTrackArea(int area) {
        mTrackArea = area;
    }
    public void setBgColor(int color) {
        mBgColor = color;
    }
    public void setKnobColor(int color) {
        mKnobColor = color;
    }

    public  int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }
}
