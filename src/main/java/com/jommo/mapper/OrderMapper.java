package com.jommo.mapper;

import com.jommo.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jommo.dto.DailyStatsDTO;
import com.jommo.dto.StatsDTO;
import com.jommo.dto.CardStats;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 不会开发的小虾米
 * @description 针对表【order】的数据库操作Mapper
 * @createDate 2025-04-30 21:48:45
 * @Entity com.jommo.domain.Order
 */
public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT COUNT(*) AS todayOrderCount, COALESCE(SUM(pay_amount), 0) AS todaySalesTotal " +
            "FROM `order` " +
            "WHERE DATE_FORMAT(payment_time,'%Y-%M-%d') = DATE_FORMAT(curdate(),'%Y-%M-%d')")
    List<CardStats> getTodaySummary();

    @Select("SELECT COUNT(*) AS yesterdayOrderCount, COALESCE(SUM(pay_amount),0) AS yesterdaySalesTotal FROM `order` WHERE DATE_FORMAT(payment_time,'%Y-%M-%d') = DATE_FORMAT(curdate() - INTERVAL 1 DAY ,'%Y-%M-%d')")
    List<CardStats> getYesterdaySummary();

    @Select("SELECT COUNT(*) FROM `order` WHERE status = #{status} AND is_deleted = 0")
    Integer getCountOfOrderByStatus(int status);

    //获取当前周订单数据
    @Select("SELECT DATE(payment_time) AS date, COUNT(*) AS orderCount, COALESCE(SUM(pay_amount), 0) AS totalAmount "
            + "FROM `order` "
            + "WHERE YEARWEEK(payment_time, 1) = YEARWEEK(CURDATE(), 1) "
            + "GROUP BY DATE(payment_time) "
            + "ORDER BY date"
    )
    List<StatsDTO> getCurrentWeekStats();

    //获取上一周订单数据
    @Select("SELECT DATE(payment_time) AS date, COUNT(*) AS orderCount, COALESCE(SUM(pay_amount), 0) AS totalAmount "
            + "FROM `order` "
            + "WHERE YEARWEEK(payment_time, 1) = YEARWEEK(CURDATE(), 1) - 1 "
            + "GROUP BY DATE(payment_time) "
            + "ORDER BY date"
    )
    List<StatsDTO> getLastWeekStats();

    //获取这一个月订单数据
    @Select("SELECT DATE(payment_time) AS date, COUNT(*) AS orderCount, COALESCE(SUM(pay_amount), 0) AS totalAmount "
            + "FROM `order` "
            + "WHERE MONTH(payment_time) = MONTH(CURDATE()) AND YEAR(payment_time) = YEAR(CURDATE()) "
            + "GROUP BY DATE(payment_time) "
            + "ORDER BY date"
    )
    List<StatsDTO> getCurrentMonthStats();

    //获取上一个月订单数据
    @Select("SELECT DATE(payment_time) AS date, COUNT(*) AS orderCount, COALESCE(SUM(pay_amount), 0) AS totalAmount "
            + "FROM `order` "
            + "WHERE MONTH(payment_time) = MONTH(CURDATE() - INTERVAL 1 MONTH) AND YEAR(payment_time) = YEAR(CURDATE() - INTERVAL 1 MONTH) "
            + "GROUP BY DATE(payment_time) "
            + "ORDER BY date"
    )
    List<StatsDTO> getLastMonthStats();

    //获取图表信息
    @Select("""
        SELECT
          DATE(payment_time) AS date,
          COUNT(*) AS orderCount,
          COALESCE(SUM(pay_amount), 0) AS totalAmount
        FROM `order`
        WHERE payment_time BETWEEN #{startDate} AND #{endDate}
        GROUP BY DATE(payment_time)
        ORDER BY date
    """)
    List<DailyStatsDTO> getStatsBetween(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);
}




