package com.jommo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.OrderReturnReason;
import com.jommo.service.OrderReturnReasonService;
import com.jommo.mapper.OrderReturnReasonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 不会开发的小虾米
 * @description 针对表【order_return_reason】的数据库操作Service实现
 * @createDate 2025-04-25 11:42:39
 */
@Service
public class OrderReturnReasonServiceImpl extends ServiceImpl<OrderReturnReasonMapper, OrderReturnReason>
        implements OrderReturnReasonService {

    @Autowired
    OrderReturnReasonMapper orderReturnReasonMapper;

    @Override
    public OrderReturnReason getByName(String name) {
        LambdaQueryWrapper<OrderReturnReason> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderReturnReason::getName, name);
        return orderReturnReasonMapper.selectOne(queryWrapper);
    }
}




