package com.jommo.domain;


import lombok.Data;

import java.util.List;

/**
 * 确认单信息封装
 * @author 不会开发的小虾米
 */
@Data
public class ConfirmOrderResult {
    //购物车商品信息
    private List<CartItem> cartItemList;
    //用户收货地址列表
    private List<MemberReceiveAddress> memberReceiveAddressList;
    //计算的金额
    private Double calcAmount;
}
