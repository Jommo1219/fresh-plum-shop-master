package com.jommo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.OrderOperateHistory;
import com.jommo.service.OrderOperateHistoryService;
import com.jommo.mapper.OrderOperateHistoryMapper;
import org.springframework.stereotype.Service;

/**
* @author 不会开发的小虾米
* @description 针对表【order_operate_history(订单操作历史表)】的数据库操作Service实现
* @createDate 2025-04-30 20:24:12
*/
@Service
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryMapper, OrderOperateHistory>
    implements OrderOperateHistoryService{

}




