package com.shenzhen.rxandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.shenzhen.rxandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FrequencyView extends View {
    // 画笔
    private Paint mPaint;
    // 频率数量
    private int mFreqCount;
    // 每个频率的宽度
    private int mFreqWidth;

    // 两个频率的间隙
    private int mFreqOffset;
    // 频率颜色
    private int mFreqColor;

    // 频率高度最大值
    private int mMax;
    // 频率高度最小值
    private int mMin;
    // 频率高度步进
    private int mStep;

    // 存放频率的集合
    private List<Frequency> mFrequencies;

    // 是否正在播放中
    private boolean isPlaying;

    public FrequencyView(Context context) {
        this(context,null);
    }

    public FrequencyView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FrequencyView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FrequencyView);
            mFreqCount = typedArray.getInteger(R.styleable.FrequencyView_freq_count, 3);
            mFreqWidth = typedArray.getDimensionPixelSize(R.styleable.FrequencyView_freq_width, 8);
            mFreqOffset = typedArray.getDimensionPixelSize(R.styleable.FrequencyView_freq_offset, 5);
            mFreqColor = typedArray.getColor(R.styleable.FrequencyView_freq_color, Color.BLUE);
            typedArray.recycle();
        } else {
            mFreqCount = 3;
            mFreqWidth = 8;
            mFreqOffset = 5;
            mFreqColor = Color.BLUE;
        }

        // 创建抗锯齿画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 设置画笔颜色
        mPaint.setColor(mFreqColor);

        // 将全部频率创建出来
         Random random = new Random();
         mFrequencies = new ArrayList<>();
         for (int i = 0; i < mFreqCount; i++) {
            // 初始频率高度随机生成，状态都为PLUS
            int freq = random.nextInt(50);
            Log.d("FrequencyView", " mFrequencies  :"+i);
            mFrequencies.add(new Frequency(freq, Frequency.PLUS));
        }
    }


    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;

        // 若正在播放，需要绘制
        if (isPlaying) {
            postInvalidate();
        }
    }
   //设置跳跃条目个数
    public void setmFreqCount(int mFreqCount) {
        this.mFreqCount = mFreqCount;
        // 将全部频率创建出来
        Random random = new Random();
        mFrequencies = new ArrayList<>();
        for (int i = 0; i < mFreqCount; i++) {
            // 初始频率高度随机生成，状态都为PLUS
            int freq = random.nextInt(50);
            Log.d("FrequencyView", " mFrequencies  :"+i);
            mFrequencies.add(new Frequency(freq, Frequency.PLUS));
        }
        invalidate();
    }

    //设置间隔宽度
    public void setmFreqOffset(int mFreqOffset) {
        this.mFreqOffset = mFreqOffset;
        invalidate();
    }
    public void setmFreqColor(int color) {
        this.mFreqColor = color;
        invalidate();
    }
   @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 给频率高度最大值赋值，需要对内边距进行处理
        mMax = h - getPaddingTop() - getPaddingBottom();
        // 给频率高度最小值赋值
        mMin = mMax / 4;
        // 给频率步进赋值
        mStep = mMin / 3;
        Log.d("onSizeChanged ",  " mMax : " +mMax + " ,mMin : "+mMin +" , mStep : "+ mStep );
    }
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mFreqCount; i++) {
            Frequency frequency = mFrequencies.get(i);
            switch (frequency.status) {
                case Frequency.PLUS:
                    /**
                     * 当前频率需要向上加时：
                     * 如果频率高度还没有到最大值，则继续向上加
                     * 否则就向下减，并将状态设置为REDUCE
                     */
                    if (frequency.freq < mMax) {
                        frequency.freq += mStep;
                    } else {
                        frequency.freq -= mStep;
                        frequency.status = Frequency.REDUCE;
                    }
                    break;
                case Frequency.REDUCE:
                    /**
                     * 当前频率需要向下减时：
                     * 如果频率高度还没有到最小值，则继续向下减
                     * 否则就向上加，并将状态设置为PLUS
                     */
                    if (frequency.freq > mMin) {
                        frequency.freq -= mStep;
                    } else {
                        frequency.freq += mStep;
                        frequency.status = Frequency.PLUS;
                    }
                    break;
                default:
                    break;
            }
            // 开始画频率，其中要考虑内边距问题，所以对内边距进行处理
            Rect rect = new Rect();
            rect.top = getHeight() - frequency.freq - getPaddingBottom();
            rect.left = i * (mFreqWidth + mFreqOffset) + getPaddingLeft();
            rect.bottom = getHeight() - getPaddingBottom();
            rect.right = i * (mFreqWidth + mFreqOffset) + getPaddingLeft() + mFreqWidth;
            canvas.drawRect(rect, mPaint);
        }
        // 若正在播放，需要重复绘制
        if (isPlaying) {
            postInvalidateDelayed(30);
        }
    }

    /**
     * 频率
     * 包括加减状态和高度
     */
    private class Frequency {
        // 频率需要向上加
        public static final int PLUS = 0;
        // 频率需要向下减
        public static final int REDUCE = 1;

        public int freq;
        public int status;

        public Frequency(int freq, int status) {
            this.freq = freq;
            this.status = status;
        }
    }

}
