package com.jommo.dto;


import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 不会开发的小虾米
 */
@Data
public class DailyStatsDTO {
    private String date;
    private Integer orderCount;
    private BigDecimal totalAmount;
}
