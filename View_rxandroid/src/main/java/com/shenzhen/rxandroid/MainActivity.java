package com.shenzhen.rxandroid;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.shenzhen.rxandroid.view.CircleImageView;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends Activity {
    private static final String TAG = "RxAndroidSamples";
    private CircleImageView mOriginView;
    private CircleImageView mReflectView;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.button_run_scheduler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String ss="abac";
//                ss=ss.replaceAll("a(\\w)","$1$1");
//                System.out.println("RxAndroidSamples :"+ss);
//
//                String ss2="vehicle_wind_down_set4";
//                ss=ss2.replace("vehicle_wind_down_set","");
//                System.out.println("RxAndroidSamples wind :"+ss);

                onRunSchedulerExampleButtonClicked();
            }
        });

       //自定义倒影的图片
        initCircleImageView();

    }

    private void initCircleImageView() {
        mOriginView = findViewById(R.id.view_circle_origin);
        mOriginView.setReflect(true);
        mOriginView.setVirtual(false);
        mOriginView.setImageResource(R.drawable.ic_launcher);

        mReflectView = findViewById(R.id.view_circle_reflect);
        mReflectView.setVirtual(true);
        mReflectView.setReflect(false);
        mReflectView.setImageResource(R.drawable.ic_launcher);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    void onRunSchedulerExampleButtonClicked() {
        disposables.add(sampleObservable()
                //Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override
                    public void onNext(String string) {
                        Log.d(TAG, "onNext(" + string + ")");
                    }
                }));
    }

    static Observable<String> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                // Do some long running operation
                SystemClock.sleep(5000);
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }
}


