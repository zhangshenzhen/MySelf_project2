package com.shenzhen.rxandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.shenzhen.rxandroid.R;

public class CircleImageView extends AppCompatImageView {

    // 画笔
    private Paint mPaint;
    // 矩阵，对图像进行变换
    private Matrix mMatrix;
    // 图像着色器
    private BitmapShader mBitmapShader;
    // 线性渐变着色器
    private LinearGradient mLinearGradient;
    // 图像重叠时的显示方式
    private Xfermode mXfermode;

    // 是否倒影
    private boolean isReflect;
    // 倒影是否虚化
    private boolean isVirtual;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
       if(attrs != null){
           TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleImageView);
           isReflect = typedArray.getBoolean(R.styleable.CircleImageView_reflect, false);
           isVirtual = typedArray.getBoolean(R.styleable.CircleImageView_virtural, false);
           typedArray.recycle();
       }else {
           isReflect = false;
           isVirtual = false;
       }

        //创建抗拒次画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
       //创建矩阵
        mMatrix = new Matrix();
      //创建及默认的线程变色器
        mLinearGradient = new LinearGradient(getWidth()/2,0,getWidth()/2,getHeight(),0,0x60ffffff, Shader.TileMode.CLAMP);
        //创建默认的图像重叠显示方式
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
    }
    public void setLinearGradient(LinearGradient gradient) {
        this.mLinearGradient = gradient;
    }
    public void setXferMode(Xfermode xfermode) {
        this.mXfermode = xfermode;
    }

    public void setReflect(boolean isReflect) {
        this.isReflect = isReflect;
    }

    public void setVirtual(boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Bitmap originBitmap = drawableToBitmap(getDrawable());
        if (originBitmap == null){
            return;
        }
        if(isReflect){
            //通过矩阵图像的ｙ坐标取反，达到镜像效果
            mMatrix.setScale(1,-1);
            //创建一个新的Bitmap
            originBitmap = Bitmap.createBitmap(originBitmap,0,0,originBitmap.getWidth(),originBitmap.getHeight(),mMatrix,false);
        }
        float scale = computeScale(originBitmap.getWidth(),originBitmap.getHeight());
        float radius = getWidth() /2;

        /**
         * 使用一个指定的图像给Paint进行着色，在绘制的时候根据设置的TitleMode模式和图像来形成不同的效果
         * 参数一：Bitmap对象
         * 参数二：水平方向的平铺模式
         * 参数三：垂直方向的平铺模式
         */
        mBitmapShader = new BitmapShader(originBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 设置缩放比例
        mMatrix.setScale(scale, scale);
        // 将矩阵设置给图像着色器，通过矩阵对图像进行变换
        mBitmapShader.setLocalMatrix(mMatrix);
        // 给画笔设置图像着色器
        mPaint.setShader(mBitmapShader);
        /**
         * 画圆
         * 参数一：圆心x坐标
         * 参数二：圆心y坐标
         * 参数三：半径
         * 参数四：画笔
         */

        if (isReflect && isVirtual) {
            mPaint.setShader(mLinearGradient);
            // 给画笔设置图像重叠时的显示方式
            mPaint.setXfermode(mXfermode);

        }

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
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

    /**
     * 计算缩放比例
     * @param originWidth
     * @param originHeight
     * @return
     */
    private float computeScale(float originWidth, float originHeight) {
        float widthScale = getWidth() / originWidth;
        float heightScale = getHeight() / originHeight;
        return Math.max(widthScale, heightScale);
    }
}
