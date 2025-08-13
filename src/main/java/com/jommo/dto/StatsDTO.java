package com.jommo.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 不会开发的小虾米
 */
@Data
public class StatsDTO {
    private Date date;
    private Integer orderCount;
    private BigDecimal totalAmount;

    @Override
    public String toString() {
        return "StatsDTO{" +
                "date=" + date +
                ", orderCount=" + orderCount +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
