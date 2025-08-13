package com.jommo.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author 不会开发的小虾米
 */
public class SerialNumberUtil {

    public static String getSerialNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //生成4位随机数
        Random r = new Random();
        int i = r.nextInt(9000) + 1000;
        return sdf.format(new Date()) + i;
    }

}
