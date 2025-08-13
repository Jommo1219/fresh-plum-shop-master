package com.jommo.dto;


import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 不会开发的小虾米
 */
@Data
public class AmountInfoParam {
    //订单ID
    private Long orderId;
    //运费
    private BigDecimal freightAmount;
    //商品状态
    private Integer status;
}
