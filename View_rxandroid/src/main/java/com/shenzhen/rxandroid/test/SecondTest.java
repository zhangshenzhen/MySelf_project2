package com.shenzhen.rxandroid.test;

public class SecondTest {


    private static SecondTest muserTest;

    public SecondTest() {

    }

    public static SecondTest getInstance(){
        if(muserTest == null){
            muserTest = new SecondTest();
        }

        return muserTest;
    }
    public void getUseTest(MySecondTest mTest){

        mTest.main("我不想回答");
    }
}
