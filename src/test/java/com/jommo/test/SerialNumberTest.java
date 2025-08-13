package com.jommo.test;


import com.jommo.util.SerialNumberUtil;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author 不会开发的小虾米
 */
public class SerialNumberTest {

    @Test
    void generateSerialNumberTest() {
        System.out.println(SerialNumberUtil.getSerialNumber());
    }

    @Test
    void generateSerialNumberTest2() {
        int randomCode = (int) (Math.random() * 9000) + 1000;
        System.out.println(randomCode); // 输出：1234
    }
}
