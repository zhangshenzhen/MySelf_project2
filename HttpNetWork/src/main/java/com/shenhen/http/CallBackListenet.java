package com.shenhen.http;

import java.io.InputStream;

interface CallBackListenet {
    void onSucess(InputStream inputStream);

    void onFailar(String message);
}
