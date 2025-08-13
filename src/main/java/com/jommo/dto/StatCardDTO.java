package com.jommo.dto;


import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 不会开发的小虾米
 */
@Data
public class StatCardDTO {
    //本月销售额
    private BigDecimal monthAmount;
    //本周销售额
    private BigDecimal weekAmount;
    //本月订单数
    private Integer monthCount;
    //本周订单数
    private Integer weekCount;
    //月销售额同比增长
    private Double monthAmountCompare;
    //周销售额同比增长
    private Double weekAmountCompare;
    //月订单数同比增长
    private Double monthCountCompare;
    //周订单数同比增长
    private Double weekCountCompare;

    @Override
    public String toString() {
        return "StatCardDTO{" +
                "本月销售额：￥" + monthAmount +
                ", 本周销售额：￥" + weekAmount +
                ", 本月订单数：" + monthCount +
                ", 本周订单数：" + weekCount +
                ", 月销售额同比增长：" + monthAmountCompare +
                "%, 周销售额同比增长：" + weekAmountCompare +
                "%, 月订单数同比增长：" + monthCountCompare +
                "%, 周订单数同比增长：" + weekCountCompare +
                "%}";
    }
}
