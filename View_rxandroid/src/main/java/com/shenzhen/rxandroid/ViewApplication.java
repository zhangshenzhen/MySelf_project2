package com.shenzhen.rxandroid;

import android.app.Application;
import android.util.Log;

import com.shenzhen.rxandroid.test.MyTest;

public class ViewApplication extends Application {
 private  String TAG = "ViewApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate .......");
    }


     private MyTest  myTest = new MyTest() {
         @Override
         public void main(String str) {
             Log.d(TAG,"MyTest .......");
         }
     };
}
