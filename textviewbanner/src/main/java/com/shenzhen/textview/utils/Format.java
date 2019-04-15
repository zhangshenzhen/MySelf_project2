package com.shenzhen.textview.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {

    /*格式化时间
     *
     * */
    public static   String fomartdate(String time){
        long t = Long.parseLong(time);
        Date date = new Date(t);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");//yyyy.MM.dd HH:mm:ss
        return format.format(date)+"";
    }
}
