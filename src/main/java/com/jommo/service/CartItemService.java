package com.jommo.service;

import com.jommo.domain.CartItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 不会开发的小虾米
* @description 针对表【cart_item】的数据库操作Service
* @createDate 2025-04-28 15:10:32
*/
public interface CartItemService extends IService<CartItem> {

    //添加商品到购物车
    void add(CartItem cartItem);

    //获取购物车中商品信息
    List<CartItem> listCartItem(Long memberId, List<Long> cartIds);
}
