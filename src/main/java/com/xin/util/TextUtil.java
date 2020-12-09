package com.xin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextUtil {
    public static int TextKind(String text) {
        if(text.startsWith("开服")) {
            return 1;
        } else if(text.startsWith("日常查询 ") || text.startsWith("日常 ")) {
            return 2;
        } else if(text.startsWith("金价 ")) {
            return 3;
        } else {
            return 0;
        }
    }


    public static String GetServerResult(int result, String server) {
        String content ;
        switch (result) {
            case 0: {
                Date now = new Date();
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
                content = "时间" + ft.format(now) + "\n" + "服务器：" + server + "\n" + "状态：未开服";
                return content;
            }
            case 1: {
                Date now = new Date();
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
                content = "时间:" + ft.format(now) + "\n" +"服务器：" + server + "\n" + "状态：已开服";
                return content;
            }
            case 2: {
                content = "输入服务器有误，请重新输入！";
                return content;
            }
        }
        return null;
    }
}
