package com.jommo.dto;


import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class ReceiverInfoParam {
    //订单ID
    private Long orderId;
    //收货人姓名
    private String receiverName;
    //收货人电话
    private String receiverPhone;
    //收货人邮编
    private String receiverPostCode;
    //详细地址
    private String receiverDetailAddress;
    //省份/直辖市
    private String receiverProvince;
    //城市
    private String receiverCity;
    //区
    private String receiverRegion;
    //订单状态
    private Integer status;
}
