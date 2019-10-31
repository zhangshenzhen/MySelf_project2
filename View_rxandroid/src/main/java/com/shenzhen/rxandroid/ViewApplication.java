package com.shenzhen.rxandroid;

import android.app.Application;
import android.util.Log;

import com.shenzhen.rxandroid.test.MySecondTest;
import com.shenzhen.rxandroid.test.MyTest;
import com.shenzhen.rxandroid.test.SecondTest;
import com.shenzhen.rxandroid.test.UserTest;

public class ViewApplication extends Application {
 private  String TAG = "ViewApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate .......");
        UserTest.getInstance().getUseTest(myTest);
    }


     private MyTest  myTest = new MyTest() {
         @Override
         public void main(String str) {
             Log.d(TAG,"MyTest ....... = : "+ str);
             SecondTest.getInstance().getUseTest(mySecondTest);
         }
     };


    private MySecondTest mySecondTest = new MySecondTest() {
        @Override
        public void main(String str) {
            Log.d(TAG,"MySecondTest ....... = : "+ str);
        }
    };
}
