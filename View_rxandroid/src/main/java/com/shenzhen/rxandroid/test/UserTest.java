package com.shenzhen.rxandroid.test;

public class UserTest {


    private static UserTest muserTest;

    public UserTest() {

    }

    public static UserTest getInstance(){
        if(muserTest == null){
            muserTest = new UserTest();
        }

        return muserTest;
    }
    public void getUseTest(MyTest mTest){

        mTest.main("我艹,别问我为什么");
    }
}
