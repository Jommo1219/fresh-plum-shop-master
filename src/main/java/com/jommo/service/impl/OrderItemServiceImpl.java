package com.jommo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.OrderItem;
import com.jommo.service.OrderItemService;
import com.jommo.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

/**
* @author 不会开发的小虾米
* @description 针对表【order_item(订单中所包含的商品)】的数据库操作Service实现
* @createDate 2025-04-29 21:12:39
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService{

}




