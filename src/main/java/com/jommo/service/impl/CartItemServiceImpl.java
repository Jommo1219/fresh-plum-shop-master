package com.jommo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.CartItem;
import com.jommo.domain.Goods;
import com.jommo.domain.Member;
import com.jommo.domain.SkuStock;
import com.jommo.mapper.GoodsMapper;
import com.jommo.mapper.MemberMapper;
import com.jommo.mapper.SkuStockMapper;
import com.jommo.service.CartItemService;
import com.jommo.mapper.CartItemMapper;
import com.jommo.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 不会开发的小虾米
 * @description 针对表【cart_item】的数据库操作Service实现
 * @createDate 2025-04-28 15:10:32
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem>
        implements CartItemService {

    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private SkuStockMapper skuStockMapper;

    @Override
    public void add(CartItem cartItem) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long memberId = (Long) map.get("id");
        Member currentMember = memberMapper.selectById(memberId);
        cartItem.setMemberId(currentMember.getId());
        cartItem.setMemberNickname(currentMember.getNickname());
        cartItem.setIsDeleted(0);
        CartItem existedCartItem = getCartItem(cartItem);
        if (existedCartItem == null) {
            cartItemMapper.insert(cartItem);
        } else {
            existedCartItem.setQuantity(existedCartItem.getQuantity() + cartItem.getQuantity());
            cartItemMapper.updateById(existedCartItem);
        }
    }

    @Override
    public List<CartItem> listCartItem(Long memberId, List<Long> cartIds) {
        LambdaQueryWrapper<CartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CartItem::getMemberId, memberId);
        List<CartItem> cartItemList = cartItemMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(cartIds)) {
            cartItemList = cartItemList.stream().filter(item -> cartIds.contains(item.getId())).toList();
        }
        //根据goodsId对cartItem进行分组
        Map<Long, List<CartItem>> goodsCartMap = groupCartItem(cartItemList);
        //查询所有商品
        List<Goods> goodsList = getGoodsList(cartItemList);
        for (Map.Entry<Long, List<CartItem>> entry : goodsCartMap.entrySet()) {
            Long goodsId = entry.getKey();
            Goods goods = getGoodsById(goodsId, goodsList);
            List<CartItem> itemList = entry.getValue();
            for (CartItem item : itemList) {
                SkuStock skuStock = skuStockMapper.selectById(item.getGoodsSkuId());
                if (skuStock != null) {
                    item.setRealStock(skuStock.getStock());
                }
            }
        }
        return cartItemList;
    }

    //根据商品id获取商品
    private Goods getGoodsById(Long goodsId, List<Goods> goodsList) {
        for (Goods goods : goodsList) {
            if (goodsId.equals(goods.getId())) {
                return goods;
            }
        }
        return null;
    }

    /**
     * 查询所有商品相关信息
     */
    private List<Goods> getGoodsList(List<CartItem> cartItemList) {
        List<Long> goodsIdList = new ArrayList<>();
        for (CartItem item : cartItemList) {
            goodsIdList.add(item.getId());
        }
        return goodsMapper.selectByIds(goodsIdList);
    }

    //对购物车中商品进行分组
    private Map<Long, List<CartItem>> groupCartItem(List<CartItem> cartItemList) {
        Map<Long, List<CartItem>> goodsCartMap = new TreeMap<>();
        for (CartItem cartItem : cartItemList) {
            List<CartItem> goodsCartItemList = goodsCartMap.get(cartItem.getGoodsId());
            if (goodsCartItemList == null) {
                goodsCartItemList = new ArrayList<>();
                goodsCartItemList.add(cartItem);
                goodsCartMap.put(cartItem.getGoodsId(), goodsCartItemList);
            } else {
                goodsCartItemList.add(cartItem);
            }

        }
        return goodsCartMap;
    }

    private CartItem getCartItem(CartItem cartItem) {
        LambdaQueryWrapper<CartItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CartItem::getMemberId, cartItem.getMemberId());
        queryWrapper.eq(CartItem::getIsDeleted, 0);
        queryWrapper.eq(CartItem::getGoodsId, cartItem.getGoodsId());
        if (cartItem.getGoodsSkuId() != null) {
            queryWrapper.eq(CartItem::getGoodsSkuId, cartItem.getGoodsSkuId());
        }
        List<CartItem> cartItemList = cartItemMapper.selectList(queryWrapper);
        if (!cartItemList.isEmpty()) {
            return cartItemList.get(0);
        }
        return null;
    }
}




