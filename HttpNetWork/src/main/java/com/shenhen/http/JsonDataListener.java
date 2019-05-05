package com.shenhen.http;

public interface JsonDataListener<T> {

    void onSucess(T enetry);
    void onFailar(String message);
}
