package com.jommo.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@Data
public class ChartDataDTO {
    private List<String> xAxis;
    private List<Long> orderSeries;
    private List<BigDecimal> salesSeries;
}
