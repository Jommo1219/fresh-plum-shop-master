package com.jommo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.common.Result;
import com.jommo.domain.ConfirmOrderResult;
import com.jommo.domain.Order;
import com.jommo.domain.OrderDetail;
import com.jommo.domain.OrderDetailWithOperateHistory;
import com.jommo.dto.*;
import com.jommo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    //根据购物车信息生成确认单
    @PostMapping("/generateConfirmOrder")
    public Result<ConfirmOrderResult> generateConfirmOrder(@RequestBody List<Long> cartIds) {
        ConfirmOrderResult confirmOrderResult = orderService.generateConfirmOrder(cartIds);
        return Result.success(confirmOrderResult);
    }

    //根据购物车信息生成订单
    @PostMapping("/generateOrder")
    @Transactional
    public Result<Map<String, Object>> generateOrder(@RequestBody OrderParam orderParam) {
        //校验收货地址
        if (orderParam.getMemberReceiveAddressId() == null) {
            throw new RuntimeException("请选择收货地址！");
        }
        Map<String, Object> res = orderService.generateOrder(orderParam);
        return Result.success(res);
    }

    //获取当前用户的订单列表
    @GetMapping("/listOrderDetail")
    public Result<List<OrderDetail>> listOrderDetail() {
        List<OrderDetail> orderDetailList = orderService.getOrderList();
        return Result.success(orderDetailList);
    }

    //获取订单详情
    @GetMapping("/detail/{id}")
    public Result<OrderDetail> detail(@PathVariable Long id) {
        OrderDetail orderDetail = orderService.detail(id);
        return Result.success(orderDetail);
    }

    //订单支付成功的回调
    @PostMapping("/paySuccess")
    @ResponseBody
    @Transactional
    public Result paySuccess(@RequestParam Long orderId, @RequestParam Integer payType) {
        orderService.paySuccess(orderId, payType);
        return Result.success("支付成功");
    }

    //后端获取所有订单
    @GetMapping("/list")
    public Result list(OrderQueryParam queryParam,
                       @RequestParam(value = "current", defaultValue = "1") Integer current,
                       @RequestParam(value = "size", defaultValue = "5") Integer size) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(
                StringUtils.isNotBlank(queryParam.getOrderSn()),
                Order::getOrderSn,
                queryParam.getOrderSn()
        );
        queryWrapper.eq(
                queryParam.getStatus() != null,
                Order::getStatus,
                queryParam.getStatus()
        );
        queryWrapper.likeRight(
                StringUtils.isNotBlank(queryParam.getCreateTime()),
                Order::getCreateTime,
                queryParam.getCreateTime()
        );
        if (StringUtils.isNotBlank(queryParam.getReceiverKeyword())) {
            queryWrapper.and(w ->
                    w.like(Order::getReceiverName, queryParam.getReceiverKeyword())
                            .or()
                            .like(Order::getReceiverPhone, queryParam.getReceiverKeyword())
            );
        }
        Page<Order> page = orderService.page(new Page<>(current, size), queryWrapper);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<OrderDetailWithOperateHistory> getOrderDetail(@PathVariable Long id) {
        OrderDetailWithOperateHistory orderDetailWithOperateHistory = orderService.getOrderDetail(id);
        return Result.success(orderDetailWithOperateHistory);
    }

    //修改收货人信息
    @PutMapping("/update/receiverInfo")
    @Transactional
    public Result updateReceiverInfo(@RequestBody ReceiverInfoParam receiverInfoParam) {
        orderService.updateReceiverInfo(receiverInfoParam);
        return Result.success();
    }

    //备注订单
    @PutMapping("/update/note")
    @Transactional
    public Result updateNote(@RequestParam("id") Long id,
                                   @RequestParam("note") String note,
                                   @RequestParam("status") Integer status) {
        orderService.updateNote(id, note, status);
        return Result.success();
    }

    //订单发货
    @PostMapping("/update/delivery")
    @Transactional
    public Result delivery(@RequestBody OrderDeliveryParam orderDeliveryParam) {
        orderService.delivery(orderDeliveryParam);
        return Result.success();
    }

    //订单关闭
    @PostMapping("/update/close")
    @Transactional
    public Result close(@RequestParam("id") Long id, @RequestParam("note") String note) {
        orderService.close(id, note);
        return Result.success();
    }

    //订单删除
    @DeleteMapping("/delete")
    public Result delete(@RequestParam("id") Long id) {
        orderService.delete(id);
        return Result.success();
    }

    //修改订单费用信息
    @PutMapping("/update/amountInfo")
    public Result updateAmountInfo(@RequestBody AmountInfoParam amountInfoParam) {
        orderService.updateAmountInfo(amountInfoParam);
        return Result.success();
    }

    //用户取消订单
    @PostMapping("/cancelUserOrder")
    public Result cancelUserOrder(@RequestParam("orderId") Long orderId) {
        orderService.cancelUserOrder(orderId);
        return Result.success();
    }

    //用户确认订单
    @PostMapping("/confirmOrder")
    public Result confirmOrder(@RequestParam("orderId") Long orderId) {
        orderService.confirmOrder(orderId);
        return Result.success();
    }


}
