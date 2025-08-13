package com.jommo.dto;


import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class OrderQueryParam {
    //订单编号
    private String orderSn;
    //收货人姓名/电话
    private String receiverKeyword;
    //订单状态
    private Integer status;
    //订单提交时间
    private String createTime;
}
