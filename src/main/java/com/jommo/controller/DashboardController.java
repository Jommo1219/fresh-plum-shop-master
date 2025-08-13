package com.jommo.controller;


import com.jommo.common.Result;
import com.jommo.dto.ChartDataDTO;
import com.jommo.dto.DailyStatsDTO;
import com.jommo.dto.DashboardDTO;
import com.jommo.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    //获取仪表盘数据
    @GetMapping
    public Result<DashboardDTO> getDashboardData() {
        DashboardDTO dashboardDTO = dashboardService.getDashboardData();
        return Result.success(dashboardDTO);
    }

    //获取图表数据
    @GetMapping("/echarts")
    public Result<List<DailyStatsDTO>> getChartData(@RequestParam("startDate")  String startDate,
                                                    @RequestParam("endDate") String endDate) {
        List<DailyStatsDTO> statsBetween = dashboardService.getStatsBetween(startDate, endDate);
        return Result.success(statsBetween);
    }

}
