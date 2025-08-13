package com.jommo.service.impl;

import ch.qos.logback.core.util.StringUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.Goods;
import com.jommo.domain.GoodsAttribute;
import com.jommo.domain.GoodsAttributeValue;
import com.jommo.domain.SkuStock;
import com.jommo.dto.GoodsDetail;
import com.jommo.dto.GoodsParam;
import com.jommo.dto.GoodsQueryParam;
import com.jommo.dto.GoodsResult;
import com.jommo.mapper.GoodsAttributeMapper;
import com.jommo.mapper.GoodsAttributeValueMapper;
import com.jommo.mapper.SkuStockMapper;
import com.jommo.service.GoodsService;
import com.jommo.mapper.GoodsMapper;
import com.jommo.util.SerialNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 不会开发的小虾米
 * @description 针对表【goods】的数据库操作Service实现
 * @createDate 2025-04-16 09:38:56
 */
@Service
public class GoodsServiceImpl extends
        ServiceImpl<GoodsMapper, Goods>
        implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private SkuStockMapper skuStockMapper;
    @Autowired
    private GoodsAttributeValueMapper goodsAttributeValueMapper;
    @Autowired
    private GoodsAttributeMapper goodsAttributeMapper;

    @Override
    public void add(GoodsParam goodsParam) {
        Goods goods = goodsParam;
        goods.setGoodsSn(SerialNumberUtil.getSerialNumber());
        goods.setId(null);
        goodsMapper.insert(goods);
        Long goodsId = goods.getId();
        //处理sku的编码
        handleSkuStockCode(goodsParam.getSkuStockList(), goodsId);
        //添加sku库存信息
        if (!CollectionUtils.isEmpty(goodsParam.getSkuStockList())) {
            for (SkuStock skuStock : goodsParam.getSkuStockList()) {
                skuStock.setId(IdWorker.getId());
                skuStock.setGoodsId(goodsId);
            }
            skuStockMapper.insertList(goodsParam.getSkuStockList());
        }
        //添加商品规格
        if (!CollectionUtils.isEmpty(goodsParam.getGoodsAttributeValueList())) {
            for (GoodsAttributeValue goodsAttributeValue : goodsParam.getGoodsAttributeValueList()) {
                goodsAttributeValue.setGoodsId(goodsId);
            }
            goodsAttributeValueMapper.insertList(goodsParam.getGoodsAttributeValueList());
        }
    }

    @Override
    public Page<Goods> list(GoodsQueryParam param, Integer current, Integer size) {
        Page<Goods> page = new Page<>(current, size);
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        if (param.getGoodsCategoryId() != null) {
            queryWrapper.eq(Goods::getGoodsCategoryId, param.getGoodsCategoryId());
        }
        if (!StringUtil.isNullOrEmpty(param.getGoodsSn())) {
            queryWrapper.eq(Goods::getGoodsSn, param.getGoodsSn());
        }
        if (!StringUtil.isNullOrEmpty(param.getKeyword())) {
            queryWrapper.like(Goods::getName, param.getKeyword());
        }
        if (param.getPublishStatus() != null) {
            queryWrapper.eq(Goods::getPublishStatus, param.getPublishStatus());
        }
        return goodsMapper.selectPage(page, queryWrapper);
    }

    @Override
    public GoodsResult getUpdateInfo(Long id) {
        return goodsMapper.getUpdateInfo(id);
    }

    @Override
    public void update(Long id, GoodsParam goodsParam) {
        Goods goods = goodsParam;
        goods.setId(id);
        goodsMapper.updateById(goods);
        //修改sku库存信息
        handleUpdateSkuStockList(id, goodsParam.getSkuStockList());
        //修改商品属性
        LambdaUpdateWrapper<GoodsAttributeValue> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GoodsAttributeValue::getGoodsId, id);
        goodsParam.getGoodsAttributeValueList().forEach(goodsAttributeValue -> {
            goodsAttributeValueMapper.update(goodsAttributeValue, updateWrapper);
        });
    }

    @Override
    public void delete(Long id) {
        LambdaQueryWrapper<SkuStock> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(SkuStock::getGoodsId, id);
        skuStockMapper.delete(queryWrapper1);
        LambdaQueryWrapper<GoodsAttributeValue> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(GoodsAttributeValue::getGoodsId, id);
        goodsAttributeValueMapper.delete(queryWrapper2);
        goodsMapper.deleteById(id);
    }

    @Override
    public GoodsDetail detail(Long id) {
        GoodsDetail result = new GoodsDetail();
        //获取商品信息
        Goods goods = goodsMapper.selectById(id);
        result.setGoods(goods);
        //获取商品属性信息
        LambdaQueryWrapper<GoodsAttribute> goodsAttrQueryWrapper = new LambdaQueryWrapper<>();
        goodsAttrQueryWrapper.eq(GoodsAttribute::getGoodsAttributeCategoryId, goods.getGoodsAttributeCategoryId())
                .orderByDesc(GoodsAttribute::getSort);
        List<GoodsAttribute> goodsAttrList = goodsAttributeMapper.selectList(goodsAttrQueryWrapper);
        result.setGoodsAttributeList(goodsAttrList);
        //获取商品属性值信息
        if (!CollectionUtils.isEmpty(goodsAttrList)) {
            List<Long> attributeIds = goodsAttrList.stream().map(GoodsAttribute::getId).collect(Collectors.toList());
            LambdaQueryWrapper<GoodsAttributeValue> goodsAttrValueQueryWrapper = new LambdaQueryWrapper<>();
            goodsAttrValueQueryWrapper.eq(GoodsAttributeValue::getGoodsAttributeId, id);
            goodsAttrValueQueryWrapper.in(GoodsAttributeValue::getGoodsAttributeId, attributeIds);
            List<GoodsAttributeValue> goodsAttributeValueList = goodsAttributeValueMapper.selectList(goodsAttrValueQueryWrapper);
            result.setGoodsAttributeValueList(goodsAttributeValueList);
        }
        //获取sku库存信息
        LambdaQueryWrapper<SkuStock> skuStockQueryWrapper = new LambdaQueryWrapper<>();
        skuStockQueryWrapper.eq(SkuStock::getGoodsId, id);
        List<SkuStock> skuStockList = skuStockMapper.selectList(skuStockQueryWrapper);
        result.setSkuStockList(skuStockList);
        return result;
    }

    private void handleSkuStockCode(List<SkuStock> skuStockList, Long goodsId) {
        if (CollectionUtils.isEmpty(skuStockList)) {
            return;
        }
        for (int i = 0; i < skuStockList.size(); i++) {
            SkuStock skuStock = skuStockList.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            StringBuilder sb = new StringBuilder();
            //日期
            sb.append(sdf.format(new Date()));
            //四位商品id
            sb.append(String.format("%04d", goodsId % 10000));
            //三位索引id
            sb.append(String.format("%03d", i + 1));
            skuStock.setSkuCode(sb.toString());
        }
    }

    private void handleUpdateSkuStockList(Long goodsId, List<SkuStock> skuStockList) {
        //当前没有sku直接删除
        if (CollUtil.isEmpty(skuStockList)) {
            LambdaQueryWrapper<SkuStock> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SkuStock::getGoodsId, goodsId);
            skuStockMapper.delete(queryWrapper);
            return;
        }
        //获取初始sku信息
        LambdaQueryWrapper<SkuStock> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SkuStock::getGoodsId, goodsId);
        List<SkuStock> oriSkuList = skuStockMapper.selectList(queryWrapper);
        queryWrapper.clear();
        //获取新增sku信息
        List<SkuStock> insertSkuList = skuStockList.stream().filter(item -> item.getId() == null).collect(Collectors.toList());
        //获取需要更新的sku信息
        List<SkuStock> updateSkuList = skuStockList.stream().filter(item -> item.getId() != null).collect(Collectors.toList());
        List<Long> updateSkuIds = updateSkuList.stream().map(SkuStock::getId).toList();
        //获取需要删除的sku信息
        List<SkuStock> removeSkuList = oriSkuList.stream().filter(item -> !updateSkuIds.contains(item.getId())).collect(Collectors.toList());
        handleSkuStockCode(insertSkuList, goodsId);
        for (SkuStock skuStock : insertSkuList) {
            skuStock.setGoodsId(goodsId);
        }
        handleSkuStockCode(updateSkuList, goodsId);
        //新增sku
        if (CollUtil.isNotEmpty(insertSkuList)) {
            skuStockMapper.insert(insertSkuList);
        }
        //删除sku
        if (CollUtil.isNotEmpty(removeSkuList)) {
            List<Long> removeSkuIds = removeSkuList.stream().map(SkuStock::getId).collect(Collectors.toList());
            skuStockMapper.deleteByIds(removeSkuIds);
        }
        //修改sku
        if (CollUtil.isNotEmpty(updateSkuList)) {
            skuStockMapper.updateById(updateSkuList);
        }
    }
}




