package com.jommo.service.impl;


import com.jommo.common.OrderStatus;
import com.jommo.common.Status;
import com.jommo.dto.*;
import com.jommo.mapper.GoodsMapper;
import com.jommo.mapper.HomeAdvertiseMapper;
import com.jommo.mapper.MemberMapper;
import com.jommo.mapper.OrderMapper;
import com.jommo.service.DashboardService;
import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private HomeAdvertiseMapper homeAdvertiseMapper;

    @Override
    public DashboardDTO getDashboardData() {
        //获取顶部卡片数据
        CardStats cardStats = orderMapper.getTodaySummary().get(0);
        CardStats yesterdayCardStats = orderMapper.getYesterdaySummary().get(0);
        cardStats.setYesterdaySalesTotal(yesterdayCardStats.getYesterdaySalesTotal());
        //获取待处理事务数据
        Integer unpaidOrder = orderMapper.getCountOfOrderByStatus(OrderStatus.UNPAID);
        Integer undeliveredOrder = orderMapper.getCountOfOrderByStatus(OrderStatus.UNDELIVERED);
        Integer deliveredOrder = orderMapper.getCountOfOrderByStatus(OrderStatus.DELIVERED);
        Integer completedOrder = orderMapper.getCountOfOrderByStatus(OrderStatus.COMPLETED);
        Integer homeAdvertiseCount = homeAdvertiseMapper.getCountOfHomeAdvertise();
        TaskDTO task = new TaskDTO();
        task.setUnpaidCount(unpaidOrder);
        task.setUndeliveredCount(undeliveredOrder);
        task.setDeliveredCount(deliveredOrder);
        task.setCompletedCount(completedOrder);
        task.setAdvertisementCount(homeAdvertiseCount);
        //获取商品总览数据
        Integer publishCount = goodsMapper.getCountOfGoodsByPublishStatus(Status.ENABLED);
        Integer unpublishCount = goodsMapper.getCountOfGoodsByPublishStatus(Status.DISABLED);
        GoodsOverviewDTO goodsOverview = new GoodsOverviewDTO();
        goodsOverview.setUnpublishCount(unpublishCount);
        goodsOverview.setPublishCount(publishCount);
        goodsOverview.setTotalCount(unpublishCount + publishCount);
        //获取用户总览数据
        Integer monthNew = memberMapper.getCountOfMonthNewMembers();
        Integer todayNew = memberMapper.getCountOfTodayNewMembers();
        Integer yesterdayNew = memberMapper.getCountOfYesterdayNewMembers();
        Integer total = memberMapper.getCountOfMember();
        MemberOverviewDTO memberOverview = new MemberOverviewDTO();
        memberOverview.setMonthNew(monthNew);
        memberOverview.setTodayNew(todayNew);
        memberOverview.setYesterdayNew(yesterdayNew);
        memberOverview.setTotalMember(total);
        //获取订单统计数据
        List<StatsDTO> currentMonthStats = orderMapper.getCurrentMonthStats();
        List<StatsDTO> lastMonthStats = orderMapper.getLastMonthStats();
        List<StatsDTO> currentWeekStats = orderMapper.getCurrentWeekStats();
        List<StatsDTO> lastWeekStats = orderMapper.getLastWeekStats();
        BigDecimal currentMonthAmount = new BigDecimal(0);
        Integer currentMonthCount = 0;
        for (StatsDTO currentMonthStat : currentMonthStats) {
            currentMonthAmount = currentMonthAmount.add(currentMonthStat.getTotalAmount());
            currentMonthCount += currentMonthStat.getOrderCount();
        }
        BigDecimal lastMonthAmount = new BigDecimal(0);
        Integer lastMonthCount = 0;
        for (StatsDTO lastMonthStat : lastMonthStats) {
            lastMonthAmount = lastMonthAmount.add(lastMonthStat.getTotalAmount());
            lastMonthCount += lastMonthStat.getOrderCount();
        }
        BigDecimal currentWeekAmount = new BigDecimal(0);
        Integer currentWeekCount = 0;
        for (StatsDTO currentWeek : currentWeekStats) {
            currentWeekAmount = currentWeekAmount.add(currentWeek.getTotalAmount());
            currentWeekCount += currentWeek.getOrderCount();
        }
        BigDecimal lastWeekAmount = new BigDecimal(0);
        Integer lastWeekCount = 0;
        for (StatsDTO lastWeek : lastWeekStats) {
            lastWeekAmount = lastWeekAmount.add(lastWeek.getTotalAmount());
            lastWeekCount += lastWeek.getOrderCount();
        }
        StatCardDTO statCardDTO = new StatCardDTO();
        statCardDTO.setMonthAmount(currentMonthAmount);
        statCardDTO.setMonthCount(currentMonthCount);
        statCardDTO.setWeekAmount(currentWeekAmount);
        statCardDTO.setWeekCount(currentWeekCount);
        //月同比
        Double monthCountCompare = null;
        Double monthAmountCompare = null;
        if (lastMonthCount.doubleValue() != 0) {
            monthCountCompare = (currentMonthCount.doubleValue() - lastMonthCount.doubleValue()) / lastMonthCount.doubleValue() * 100;
            monthCountCompare = new BigDecimal(monthCountCompare).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        if (lastMonthAmount.doubleValue() != 0) {
            monthAmountCompare = (currentMonthAmount.doubleValue() - lastMonthAmount.doubleValue()) / lastMonthAmount.doubleValue() * 100;
            monthAmountCompare = new BigDecimal(monthAmountCompare).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        statCardDTO.setMonthCountCompare(monthCountCompare);
        statCardDTO.setMonthAmountCompare(monthAmountCompare);
        // 周同比
        Double weekCountCompare = null;
        Double weekAmountCompare = null;
        if (lastWeekCount.doubleValue() != 0) {
            weekCountCompare = (currentWeekCount.doubleValue() - lastWeekCount.doubleValue()) / lastWeekCount.doubleValue() * 100;
            weekCountCompare = new BigDecimal(weekCountCompare).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        if (lastWeekAmount.doubleValue() != 0) {
            weekAmountCompare = (currentWeekAmount.doubleValue() - lastWeekAmount.doubleValue()) / lastWeekAmount.doubleValue() * 100;
            weekAmountCompare = new BigDecimal(weekAmountCompare).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        statCardDTO.setWeekCountCompare(weekCountCompare);
        statCardDTO.setWeekAmountCompare(weekAmountCompare);
        //返回数据
        cardStats.setUndeliveredCount(undeliveredOrder);
        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setCardStats(cardStats);
        dashboardDTO.setMemberOverview(memberOverview);
        dashboardDTO.setGoodsOverview(goodsOverview);
        dashboardDTO.setStatCard(statCardDTO);
        dashboardDTO.setTask(task);
        return dashboardDTO;
    }

    @Override
    public List<DailyStatsDTO> getStatsBetween(String startDate, String endDate) {
        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atTime(LocalTime.MAX);
        return orderMapper.getStatsBetween(start, end);
    }
}
