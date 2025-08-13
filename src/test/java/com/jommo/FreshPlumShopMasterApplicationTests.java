package com.jommo;

import com.jommo.common.OrderStatus;
import com.jommo.dto.StatCardDTO;
import com.jommo.dto.StatsDTO;
import com.jommo.dto.CardStats;
import com.jommo.dto.TaskDTO;
import com.jommo.mapper.HomeAdvertiseMapper;
import com.jommo.mapper.OrderMapper;
import com.jommo.util.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class FreshPlumShopMasterApplicationTests {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private HomeAdvertiseMapper homeAdvertiseMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void Md5Test() {
        System.out.println(Md5Util.getMD5String("123123"));
    }

    @Test
    void orderMapperTest() {

    }
}
