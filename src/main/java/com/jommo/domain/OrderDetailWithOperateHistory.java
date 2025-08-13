package com.jommo.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@Data
public class OrderDetailWithOperateHistory extends Order{
    //订单商品列表
    private List<OrderItem> orderItemList;
    //订单操作记录列表
    private List<OrderOperateHistory> historyList;
}
