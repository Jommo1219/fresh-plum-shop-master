package com.jommo.common;


/**
 * @author 不会开发的小虾米
 */
public interface OrderStatus {
    Integer UNPAID = 0;
    Integer UNDELIVERED = 1;
    Integer DELIVERED = 2;
    Integer COMPLETED = 3;
    Integer CANCELED = 4;
    Integer INVALID = 5;
}
