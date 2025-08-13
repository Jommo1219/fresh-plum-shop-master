package com.jommo.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDetail extends Order {
    //订单商品列表
    private List<OrderItem> orderItemList;
}
