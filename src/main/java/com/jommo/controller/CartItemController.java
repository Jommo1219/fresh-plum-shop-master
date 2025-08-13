package com.jommo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jommo.common.Result;
import com.jommo.domain.CartItem;
import com.jommo.service.CartItemService;
import com.jommo.service.MemberService;
import com.jommo.util.ThreadLocalUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private MemberService memberService;

    //添加
    @PostMapping("/add")
    public Result add(@RequestBody CartItem cartItem) {
        cartItemService.add(cartItem);
        return Result.success();
    }

    //根据用户id获取购物车
    @GetMapping("/list")
    public Result<List<CartItem>> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long memberId = (Long) map.get("id");
        LambdaQueryWrapper<CartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CartItem::getMemberId, memberId);
        List<CartItem> list = cartItemService.list(queryWrapper);
        return Result.success(list);
    }

    //修改商品数量
    @PutMapping("/update/quantity")
    public Result updateQuantity(@RequestParam(name = "id") @NotNull(message = "ID不能为空") Long id, @RequestParam(name = "quantity") Integer quantity) {
        LambdaUpdateWrapper<CartItem> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CartItem::getId, id);
        updateWrapper.set(CartItem::getQuantity, quantity);
        cartItemService.update(updateWrapper);
        return Result.success();
    }

    //删除指定商品
    @DeleteMapping("/delete")
    public Result delete(@RequestParam(name = "id") Long id) {
        cartItemService.removeById(id);
        return Result.success();
    }

    //清空购物车
    @DeleteMapping("/clear")
    public Result clear() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long memberId = (Long) map.get("id");
        LambdaQueryWrapper<CartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CartItem::getMemberId, memberId);
        cartItemService.remove(queryWrapper);
        return Result.success();
    }

    //获取购物车数量
    @GetMapping("/count")
    public Result cartCount() {
        Map<String,Object> map = ThreadLocalUtil.get();
        if (map != null) {
            Long memberId = (Long) map.get("id");
            LambdaQueryWrapper<CartItem> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CartItem::getMemberId, memberId);
            long count = cartItemService.count(queryWrapper);
            return Result.success(count);
        }
        return Result.success(0);
    }
}
