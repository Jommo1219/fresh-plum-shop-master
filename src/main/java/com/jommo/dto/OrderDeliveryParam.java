package com.jommo.dto;


import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class OrderDeliveryParam {
    //订单ID
    private Long orderId;
    //物流公司
    private String deliveryCompany;
    //物流单号
    private String deliverySn;
}
