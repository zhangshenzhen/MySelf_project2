package com.zyw.horrarndoo.cirqueControlView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Toast toast;
    private CirqueProgressControlView ccv;
    private CirqueProgressControlViewNew cv;
    public Button jia ,jian;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    减(ccv);
                    break;
                case 2:
                    加(ccv);
                    break;
            }
        }
    };

    private Runnable jiaRunable = new Runnable() {
        @Override
        public void run() {
            加(ccv);
        }
    };
    public boolean isLongClick;
    public ScheduledThreadPoolExecutor mThreadPoolExecutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initLongClick();
    }

    /*
      *
      * */
    private void init() {
        jia = (Button) findViewById(R.id.jia);
        jian = (Button) findViewById(R.id.jian);
        ccv = (CirqueProgressControlView) findViewById(R.id.ccv);
        ccv.setProgressRange(0, 100);//可以在xml中指定，也可以在代码中设置
        //        ccv.setIsAnim(false);//这个方法必须在setProgress之前执行
        ccv.setProgress(ccv.mCurrentProgress);  //添加默认数据--注:不能超出范围
        ccv.setOnTextFinishListener(new CirqueProgressControlView.OnCirqueProgressChangeListener() {
            @Override
            public void onChange(int minProgress, int maxProgress, int progress) {
                showToast(MainActivity.this, progress + "");
            }

            @Override
            public void onChangeEnd(int minProgress, int maxProgress, int progress) {
                showToast(MainActivity.this, "control finish. last progress is " + progress + "");
            }
        });

        cv = (CirqueProgressControlViewNew) findViewById(R.id.cv);
        cv.setChangeListener(new CirqueProgressControlViewNew.OnProgressChangeListener() {
            @Override
            public void onProgressChange(int duration, int progress) {
                showToast(MainActivity.this, "progress = " + progress);
            }

            @Override
            public void onProgressChangeEnd(int duration, int progress) {
                showToast(MainActivity.this, "duration = " + duration + ",progress = " + progress);
            }
        });


    }

    private void initLongClick() {
        jia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 switch (event.getAction()){
                     case MotionEvent.ACTION_UP:
                         if(isLongClick){
                          mThreadPoolExecutor.shutdown();
                         }
                         isLongClick = false;
                         break;
                 }

                return false;
            }
        });
        jia.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("onLongClick",">>>>>>>>>>>>>>>>");
                isLongClick = true;
                if(mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown()){
                  mThreadPoolExecutor = new ScheduledThreadPoolExecutor(3);
                }
                mThreadPoolExecutor.scheduleWithFixedDelay(jiaRunable,500,500,  TimeUnit.MILLISECONDS);
                return false;
            }
        });
    }



    public void 减(View view){
        ccv.mCurrentProgress--;
        if(ccv.mCurrentProgress<0){
            ccv.mCurrentProgress =0;
        }
        ccv.setIsAnim(false);//关闭动画，直接在基础上变化
        ccv.setProgress(ccv.mCurrentProgress);


    }
    public void 加(View view){
        ccv.mCurrentProgress++;
        if(ccv.mCurrentProgress>100){
            ccv.mCurrentProgress =100;
        }
        ccv.setIsAnim(false);//关闭动画，直接在基础上变化
        ccv.setProgress(ccv.mCurrentProgress);
    }


    private void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
