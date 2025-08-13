package com.jommo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.common.OrderStatus;
import com.jommo.common.Status;
import com.jommo.domain.*;
import com.jommo.dto.AmountInfoParam;
import com.jommo.dto.OrderDeliveryParam;
import com.jommo.dto.OrderParam;
import com.jommo.dto.ReceiverInfoParam;
import com.jommo.mapper.*;
import com.jommo.service.CartItemService;
import com.jommo.service.OrderService;
import com.jommo.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 不会开发的小虾米
 * @description 针对表【order】的数据库操作Service实现
 * @createDate 2025-04-29 16:09:42
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
    private MemberReceiveAddressMapper memberReceiveAddressMapper;
    @Autowired
    private SkuStockMapper skuStockMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderOperateHistoryMapper orderOperateHistoryMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public ConfirmOrderResult generateConfirmOrder(List<Long> cartIds) {
        ConfirmOrderResult result = new ConfirmOrderResult();
        //获取购物车信息
        List<CartItem> cartItemList = cartItemMapper.selectByIds(cartIds);
        result.setCartItemList(cartItemList);
        //获取用户收货地址信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Long memberId = (Long) map.get("id");
        LambdaQueryWrapper<MemberReceiveAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberReceiveAddress::getMemberId, memberId);
        List<MemberReceiveAddress> memberReceiveAddressList = memberReceiveAddressMapper.selectList(queryWrapper);
        result.setMemberReceiveAddressList(memberReceiveAddressList);
        //计算购物车商品总价格
        Double totalAmount = calcCartAmount(cartItemList);
        result.setCalcAmount(totalAmount);
        return result;
    }

    @Override
    public Map<String, Object> generateOrder(OrderParam orderParam) {
        List<OrderItem> orderItemList = new ArrayList<>();
        //获取购物车信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Long memberId = (Long) map.get("id");
        List<CartItem> cartItemList = cartItemService.listCartItem(memberId, orderParam.getCartIds());
        for (CartItem cartItem : cartItemList) {
            OrderItem orderItem = getOrderItem(cartItem);
            orderItemList.add(orderItem);
        }
        //判断购物车中商品是否都有库存
        if (!hasStock(cartItemList)) {
            throw new RuntimeException("库存不足，无法下单");
        }
        //计算orderItem的实付金额
        handleRealAmount(orderItemList);
        //锁定库存
        lockStock(cartItemList);
        Order order = new Order();
        order.setTotalAmount(calcTotalAmount(orderItemList));
        order.setPayAmount(order.getTotalAmount());
        //转化为订单信息并插入数据库
        order.setMemberId(memberId);
        order.setMemberUsername((String) map.get("username"));
        order.setStatus(0);
        //收货人信息
        MemberReceiveAddress address = memberReceiveAddressMapper.selectById(orderParam.getMemberReceiveAddressId());
        order.setReceiverName(address.getName());
        order.setReceiverPhone(address.getPhone());
        order.setReceiverPostCode(address.getPostCode());
        order.setReceiverProvince(address.getProvince());
        order.setReceiverCity(address.getCity());
        order.setReceiverRegion(address.getRegion());
        order.setReceiverDetailAddress(address.getDetailAddress());
        //设置订单状态
        order.setConfirmStatus(0);
        order.setIsDeleted(0);
        //生成订单号
        order.setOrderSn(generateOrderSn());
        order.setNote(orderParam.getRemark());
        orderMapper.insert(order);
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
        }
        orderItemMapper.insert(orderItemList);
        //删除购物车中的下单商品
        deleteCartItemList(cartItemList, memberId);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("order", order);
        resMap.put("orderItemList", orderItemList);
        return resMap;
    }

    @Override
    public List<OrderDetail> getOrderList() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long memberId = (Long) map.get("id");
        LambdaQueryWrapper<Order> orderQueryWrapper = new LambdaQueryWrapper<>();
        orderQueryWrapper.eq(Order::getMemberId, memberId)
                .orderByDesc(Order::getCreateTime);
        List<Order> orderList = orderMapper.selectList(orderQueryWrapper);
        //设置数据信息
        List<Long> orderIds = orderList.stream().map(Order::getId).toList();
        LambdaQueryWrapper<OrderItem> orderItemQueryWrapper = new LambdaQueryWrapper<>();
        orderItemQueryWrapper.in(OrderItem::getOrderId, orderIds);
        List<OrderItem> orderItemList = orderItemMapper.selectList(orderItemQueryWrapper);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (Order order : orderList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtil.copyProperties(order, orderDetail);
            List<OrderItem> relatedItemList = orderItemList.stream().filter(item -> item.getOrderId().equals(orderDetail.getId())).collect(Collectors.toList());
            orderDetail.setOrderItemList(relatedItemList);
            orderDetailList.add(orderDetail);
        }
        return orderDetailList;
    }

    @Override
    public OrderDetail detail(Long id) {
        Order order = orderMapper.selectById(id);
        LambdaQueryWrapper<OrderItem> orderItemQueryWrapper = new LambdaQueryWrapper<>();
        orderItemQueryWrapper.eq(OrderItem::getOrderId, id);
        List<OrderItem> orderItemList = orderItemMapper.selectList(orderItemQueryWrapper);
        OrderDetail orderDetail = new OrderDetail();
        BeanUtil.copyProperties(order, orderDetail);
        orderDetail.setOrderItemList(orderItemList);
        return orderDetail;
    }

    @Override
    public void paySuccess(Long orderId, Integer payType) {
        LambdaUpdateWrapper<Order> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        orderLambdaUpdateWrapper.eq(Order::getId, orderId)
                .eq(Order::getStatus, OrderStatus.UNPAID)
                .set(Order::getStatus, OrderStatus.UNDELIVERED)
                .set(Order::getPaymentTime, new Date())
                .set(Order::getPayType, payType);
        int count = orderMapper.update(orderLambdaUpdateWrapper);
        if (count == 0) {
            throw new RuntimeException("订单不存在或订单状态不是未支付！");
        }
        //恢复所有下单商品的锁定库存，扣减真实库存
        OrderDetail detail = detail(orderId);
        for (OrderItem orderItem : detail.getOrderItemList()) {
            int updateCount = skuStockMapper.reduceStock(orderItem.getGoodsSkuId(), orderItem.getGoodsQuantity());
            if (updateCount == 0) {
                throw new RuntimeException("库存不足，无法支付");
            }
        }
    }

    @Override
    public OrderDetailWithOperateHistory getOrderDetail(Long id) {
        OrderDetailWithOperateHistory orderDetailWithOperateHistory = new OrderDetailWithOperateHistory();
        //查询订单信息
        Order order = orderMapper.selectById(id);
        BeanUtil.copyProperties(order, orderDetailWithOperateHistory);
        //查询订单商品信息
        LambdaQueryWrapper<OrderItem> orderItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderItemLambdaQueryWrapper.eq(OrderItem::getOrderId, id);
        List<OrderItem> orderItemList = orderItemMapper.selectList(orderItemLambdaQueryWrapper);
        orderDetailWithOperateHistory.setOrderItemList(orderItemList);
        //查询订单历史操作信息
        LambdaQueryWrapper<OrderOperateHistory> orderOperateHistoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderOperateHistoryLambdaQueryWrapper.eq(OrderOperateHistory::getOrderId, id);
        List<OrderOperateHistory> orderOperateHistories = orderOperateHistoryMapper.selectList(orderOperateHistoryLambdaQueryWrapper);
        orderDetailWithOperateHistory.setHistoryList(orderOperateHistories);
        return orderDetailWithOperateHistory;
    }

    @Override
    public void updateReceiverInfo(ReceiverInfoParam receiverInfoParam) {
        Order order = new Order();
        order.setId(receiverInfoParam.getOrderId());
        order.setReceiverName(receiverInfoParam.getReceiverName());
        order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
        order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
        order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
        order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
        order.setReceiverCity(receiverInfoParam.getReceiverCity());
        order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        orderMapper.updateById(order);
        //插入操作记录
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(receiverInfoParam.getOrderId());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(receiverInfoParam.getStatus());
        history.setNote("修改收货人信息");
        orderOperateHistoryMapper.insert(history);
    }

    @Override
    public void updateNote(Long id, String note, Integer status) {
        Order order = new Order();
        order.setId(id);
        order.setNote(note);
        orderMapper.updateById(order);
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(id);
        history.setOperateMan("后台管理员");
        history.setOrderStatus(status);
        history.setNote("修改备注信息：" + note);
        orderOperateHistoryMapper.insert(history);
    }

    @Override
    public void delivery(OrderDeliveryParam orderDeliveryParam) {
        LambdaUpdateWrapper<Order> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Order::getId, orderDeliveryParam.getOrderId());
        updateWrapper.set(Order::getStatus, OrderStatus.DELIVERED);
        updateWrapper.set(Order::getDeliveryTime, new Date());
        updateWrapper.set(Order::getDeliveryCompany, orderDeliveryParam.getDeliveryCompany());
        updateWrapper.set(Order::getDeliverySn, orderDeliveryParam.getDeliverySn());
        orderMapper.update(updateWrapper);
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(orderDeliveryParam.getOrderId());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(OrderStatus.DELIVERED);
        history.setNote("完成发货");
        orderOperateHistoryMapper.insert(history);
    }

    @Override
    public void close(Long id, String note) {
        Order record = new Order();
        record.setStatus(OrderStatus.CANCELED);
        record.setId(id);
        orderMapper.updateById(record);
        //恢复库存
        OrderDetail detail = detail(id);
        for (OrderItem item : detail.getOrderItemList()) {
            skuStockMapper.recoverStock(item.getGoodsSkuId(), item.getGoodsQuantity());
        }
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(id);
        history.setOperateMan("后台管理员");
        history.setOrderStatus(OrderStatus.CANCELED);
        history.setNote("订单关闭:" + note);
        orderOperateHistoryMapper.insert(history);
    }

    @Override
    public void delete(Long id) {
        orderMapper.deleteById(id);
    }

    @Override
    public void updateAmountInfo(AmountInfoParam amountInfoParam) {
        Order order = new Order();
        order.setId(amountInfoParam.getOrderId());
        order.setFreightAmount(amountInfoParam.getFreightAmount());
        orderMapper.updateById(order);
        //插入操作记录
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(amountInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(amountInfoParam.getStatus());
        history.setNote("修改费用信息");
        orderOperateHistoryMapper.insert(history);
    }

    @Override
    public void cancelUserOrder(Long orderId) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getId, orderId)
                .eq(Order::getStatus, OrderStatus.UNPAID);
        Order order = orderMapper.selectOne(queryWrapper);
        if (Objects.isNull(order)) {
            return;
        }
        //修改订单状态
        order.setStatus(OrderStatus.CANCELED);
        orderMapper.updateById(order);
        LambdaQueryWrapper<OrderItem> orderItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderItemLambdaQueryWrapper.eq(OrderItem::getOrderId, orderId);
        List<OrderItem> orderItemList = orderItemMapper.selectList(orderItemLambdaQueryWrapper);
        //解除订单商品库存锁定
        if (!CollectionUtils.isEmpty(orderItemList)) {
            for (OrderItem orderItem : orderItemList) {
                int count = skuStockMapper.releaseStockBySkuId(orderItem.getGoodsSkuId(), orderItem.getGoodsQuantity());
                if (count == 0) {
                    throw new RuntimeException("库存不足，无法取消");
                }
            }
        }
    }

    @Override
    public void confirmOrder(Long orderId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long memberId = (Long) map.get("id");
        Order order = orderMapper.selectById(orderId);
        if (!memberId.equals(order.getMemberId())) {
            throw new RuntimeException("不能确认他人订单！");
        }
        if (!Objects.equals(order.getStatus(), OrderStatus.DELIVERED)) {
            throw new RuntimeException("该订单还未发货！");
        }
        order.setStatus(OrderStatus.COMPLETED);
        order.setConfirmStatus(Status.ENABLED);
        order.setReceiveTime(new Date());
        orderMapper.updateById(order);
        //更新商品销量
        OrderDetail detail = detail(orderId);
        for(OrderItem item : detail.getOrderItemList()) {
            skuStockMapper.updateSale(item.getGoodsSkuId(), item.getGoodsQuantity());
            goodsMapper.updateSale(item.getGoodsId(), item.getGoodsQuantity());
        }
    }

    private void deleteCartItemList(List<CartItem> cartItemList, Long memberId) {
        List<Long> ids = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            ids.add(cartItem.getId());
        }
        LambdaQueryWrapper<CartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CartItem::getId, ids);
        queryWrapper.eq(CartItem::getMemberId, memberId);
        cartItemMapper.delete(queryWrapper);
    }

    /**
     * 生成18位订单编号:14位日期4位随机数
     */
    private String generateOrderSn() {
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        sb.append(date);
        int random = (int) (Math.random() * 9000) + 1000;
        sb.append(random);
        return sb.toString();
    }

    //计算总金额
    private BigDecimal calcTotalAmount(List<OrderItem> orderItemList) {
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getTotalAmount());
        }
        return totalAmount;
    }

    private void handleRealAmount(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            BigDecimal totalAmount = orderItem.getGoodsPrice();
            orderItem.setTotalAmount(totalAmount);
        }
    }

    /**
     * 锁定下单商品的所有库存
     */
    private void lockStock(List<CartItem> cartItemList) {
        for (CartItem cartItem : cartItemList) {
            SkuStock skuStock = skuStockMapper.selectById(cartItem.getGoodsSkuId());
            skuStock.setLockStock(skuStock.getLockStock() + cartItem.getQuantity());
            LambdaUpdateWrapper<SkuStock> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SkuStock::getId, skuStock.getId())
                    .set(SkuStock::getLockStock, skuStock.getLockStock());
            int count = skuStockMapper.update(updateWrapper);
            if (count == 0) {
                throw new RuntimeException("库存不足，无法下单");
            }
        }
    }

    //判断购物车中商品是否都有库存
    private boolean hasStock(List<CartItem> cartItemList) {
        for (CartItem cartItem : cartItemList) {
            //判断真实库存是否为空
            if (cartItem.getRealStock() == null
                    //判断真是库存是否小于0
                    || cartItem.getRealStock() <= 0
                    //判断真实库存是否小于下单的数量
                    || cartItem.getRealStock() < cartItem.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    //生成下单商品信息
    private static OrderItem getOrderItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setGoodsId(cartItem.getGoodsId());
        orderItem.setGoodsName(cartItem.getGoodsName());
        orderItem.setGoodsPic(cartItem.getGoodsPic());
        orderItem.setGoodsAttr(cartItem.getGoodsAttr());
        orderItem.setGoodsSn(cartItem.getGoodsSn());
        orderItem.setGoodsPrice(cartItem.getPrice());
        orderItem.setGoodsQuantity(cartItem.getQuantity());
        orderItem.setGoodsSkuId(cartItem.getGoodsSkuId());
        orderItem.setGoodsSkuCode(cartItem.getGoodsSkuCode());
        orderItem.setGoodsCategoryId(cartItem.getGoodsCategoryId());
        return orderItem;
    }

    /**
     * 计算购物车中商品的价格
     */
    private Double calcCartAmount(List<CartItem> cartItemList) {
        BigDecimal totalAmount = new BigDecimal(0);
        for (CartItem cartItem : cartItemList) {
            totalAmount = totalAmount.add(cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        }
        return Double.parseDouble(totalAmount.toString());
    }
}




