package com.shenzhen.rxandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.shenzhen.rxandroid.R;

public class MyMatrix extends android.support.v7.widget.AppCompatImageView {



    public Paint mPaint;
    public boolean istranslate = false;//是否平移
    public boolean isScale = false;//是否缩放

    public boolean isRotate = false;//旋转

    public boolean isshape = false;//拉伸变形

    public MyMatrix(Context context) {
        this(context,null);
    }

    public MyMatrix(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyMatrix(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMyMatrix(attrs);
    }

    private void initMyMatrix(AttributeSet attrs) {

        if(attrs != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyMatrix);
            istranslate = typedArray.getBoolean(R.styleable.MyMatrix_istranslate, false);
            isScale = typedArray.getBoolean(R.styleable.MyMatrix_isScale, false);
            isRotate = typedArray.getBoolean(R.styleable.MyMatrix_isRotate, false);
            isshape = typedArray.getBoolean(R.styleable.MyMatrix_isshape, false);
            typedArray.recycle();
        }

        //创建抗拒次画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    //是否平移
    public void setTranslate(boolean istranslate) {
        this.istranslate = istranslate;
    }
    //是否缩放
    public void setScale(boolean scale) {
        isScale = scale;
    }
  //旋转
    public void setRotate(boolean rotate) {
        isRotate = rotate;
    }
    public void setIsshape(boolean isshape) {
        this.isshape = isshape;
    }


    @Override
    protected void onDraw(Canvas canvas) {
       // super.onDraw(canvas);

        Bitmap originBitmap = null;
          originBitmap  =  drawableToBitmap(getDrawable());

        //设置画布背景颜色
     //   canvas.drawColor(Color.GRAY);

        //创建matrix
        Matrix matrix = new Matrix();

        canvas.drawBitmap(originBitmap,matrix,mPaint);

        /**
         * 对matrix进行平移，再次绘制图片
         * 参数1：x方向的平移量
         * 参数2：y方向的平移量
         */
        if(istranslate){
          matrix.setTranslate(getMeasuredWidth()/2,getMeasuredHeight()/2);
          canvas.drawBitmap(originBitmap,matrix,mPaint);
        }

        /**
         * 对矩阵进行缩放
         * 参数1：x方向缩放倍数
         * 参数2：y方向缩放倍数
         * 参数3：x方向缩放坐标
         * 参数4：y方向缩放坐标
         */
        if(isScale){
         matrix.setScale(1.5f,1.5f,originBitmap.getWidth()/2,originBitmap.getHeight()/2);
         matrix.postTranslate(originBitmap.getHeight()*1.5f,originBitmap.getWidth()*1.5f);
         matrix. preRotate(-45);//旋转
       // matrix.setTranslate(120,120);//平移 会覆盖 postTranslate
         canvas.drawBitmap(originBitmap,matrix,mPaint);
        }

        /**
         * 对图片进行旋转操作（顺时针方向进行）
         * 参数1：旋转角度
         * 参数2：中心点的x坐标
         * 参数3：中心点的y轴坐标
         */
        if (isRotate) {
            matrix.setRotate(45, originBitmap.getWidth() / 2, originBitmap.getHeight() / 2);
            //平移图片到控件中心方便观察
            matrix.postTranslate(getMeasuredWidth() / 2 - originBitmap.getWidth() / 2, getMeasuredHeight() / 2 - originBitmap.getHeight() / 2);
            canvas.drawBitmap(originBitmap, matrix, mPaint);
        }

         //变形
        if (isshape) {
            int bw = originBitmap.getWidth();
            int bh = originBitmap.getHeight();

            float[] dst = new float[8];
            float[] src = new float[8];

            /**
             * 原始坐标
             */
            src[0] = 0;
            src[1] = 0;
            src[2] = bw;
            src[3] = 0;
            src[4] = 0;
            src[5] = bh;
            src[6] = bw;
            src[7] = bh;

            /**
             * 新坐标
             * 这里我们只改变第四个点的坐标（没什么意外的话会出现一个不规则的菱形）
             */
            dst[0] = 0;
            dst[1] = 0;
            dst[2] = bw;
            dst[3] = 0;
            dst[4] = 0;
            dst[5] = bh;

            //最后一个点的坐标xy都增加两倍
            dst[6] = bw * 2;
            dst[7] = bh * 2;

            matrix.setPolyToPoly(src, 0, dst, 0, 4);
            canvas.drawBitmap(originBitmap, matrix, mPaint);
        }

    }

    /**
     * Drawable 转 Bitmap
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }
}
