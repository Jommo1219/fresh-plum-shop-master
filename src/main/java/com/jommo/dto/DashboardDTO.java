package com.jommo.dto;


import lombok.Data;

import java.util.List;

/**
 * 后端返回给前端的整体仪表盘数据
 * @author 不会开发的小虾米
 */
@Data
public class DashboardDTO {
    private CardStats cardStats;
    private GoodsOverviewDTO goodsOverview;
    private MemberOverviewDTO memberOverview;
    private TaskDTO task;
    private StatCardDTO statCard;
    private ChartDataDTO chartData;
}
