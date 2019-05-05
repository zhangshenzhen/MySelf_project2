package com.shenhen.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class InputStreamUtils {

    public static String ScreamToString(InputStream in) throws Exception {
        //定义一个内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] bt = new byte[1024];
        while ((len = in.read(bt)) != -1) {
            out.write(bt, 0, len);
        }
        String content = new String(out.toByteArray());
      // String content=new String(out.toByteArray(),"gbk");//使用构造函数,不指定编码，默认是utf-8
        return content;
    }

}
