package com.jommo.dto;


import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 不会开发的小虾米
 */
@Data
public class CardStats {
    private Integer todayOrderCount;
    private BigDecimal todaySalesTotal;
    private BigDecimal yesterdaySalesTotal;
    private Integer undeliveredCount;

    @Override
    public String toString() {
        return "SummaryStats{" +
                "todayOrderCount=" + todayOrderCount +
                ", todaySalesTotal=" + todaySalesTotal +
                ", yesterdaySalesTotal=" + yesterdaySalesTotal +
                ", pendingShipmentCount=" + undeliveredCount +
                '}';
    }
}
