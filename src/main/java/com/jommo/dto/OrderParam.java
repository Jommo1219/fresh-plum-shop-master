package com.jommo.dto;


import lombok.Data;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@Data
public class OrderParam {
    //收货地址id
    private Long memberReceiveAddressId;
    //被选中购物车商品ID
    private List<Long> cartIds;
    //订单备注
    private String remark;
}
