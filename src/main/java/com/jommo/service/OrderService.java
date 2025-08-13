package com.jommo.service;

import com.jommo.domain.ConfirmOrderResult;
import com.jommo.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jommo.domain.OrderDetail;
import com.jommo.domain.OrderDetailWithOperateHistory;
import com.jommo.dto.AmountInfoParam;
import com.jommo.dto.OrderDeliveryParam;
import com.jommo.dto.OrderParam;
import com.jommo.dto.ReceiverInfoParam;

import java.util.List;
import java.util.Map;

/**
* @author 不会开发的小虾米
* @description 针对表【order】的数据库操作Service
* @createDate 2025-04-29 16:09:42
*/
public interface OrderService extends IService<Order> {

    //根据购物车商品信息生成确认订单
    ConfirmOrderResult generateConfirmOrder(List<Long> cartIds);

    //根据购物车商品信息生成订单
    Map<String, Object> generateOrder(OrderParam orderParam);

    //获取订单列表
    List<OrderDetail> getOrderList();

    //获取订单详情
    OrderDetail detail(Long id);

    //支付成功的回调函数
    void paySuccess(Long orderId, Integer payType);

    //获取订单详情：订单信息、商品信息、操作记录
    OrderDetailWithOperateHistory getOrderDetail(Long id);

    //修改收件人信息
    void updateReceiverInfo(ReceiverInfoParam receiverInfoParam);

    //备注订单
    void updateNote(Long id, String note, Integer status);

    //订单发货
    void delivery(OrderDeliveryParam orderDeliveryParam);

    //关闭订单
    void close(Long id, String note);

    //删除订单
    void delete(Long id);

    //修改订单费用信息
    void updateAmountInfo(AmountInfoParam amountInfoParam);

    //用户取消订单
    void cancelUserOrder(Long orderId);

    //用户确认订单
    void confirmOrder(Long orderId);
}
