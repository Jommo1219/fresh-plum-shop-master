package com.jommo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 不会开发的小虾米
 */
@SpringBootApplication
@Transactional
public class FreshPlumShopMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreshPlumShopMasterApplication.class, args);
    }

}
