package com.jommo.service;


import com.jommo.dto.DailyStatsDTO;
import com.jommo.dto.DashboardDTO;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
public interface DashboardService {
    //获取仪表盘数据
    DashboardDTO getDashboardData();

    //获取图表信息
    List<DailyStatsDTO> getStatsBetween(String startDate, String endDate);

}
