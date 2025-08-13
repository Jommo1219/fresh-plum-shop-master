package com.jommo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.domain.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jommo.dto.GoodsDetail;
import com.jommo.dto.GoodsParam;
import com.jommo.dto.GoodsQueryParam;
import com.jommo.dto.GoodsResult;

/**
* @author 不会开发的小虾米
* @description 针对表【goods】的数据库操作Service
* @createDate 2025-04-16 09:38:56
*/
public interface GoodsService extends IService<Goods> {

    //添加商品
    void add(GoodsParam goodsParam);

    //根据条件分页查询
    Page<Goods> list(GoodsQueryParam param, Integer current, Integer size);

    //获取商品编辑信息
    GoodsResult getUpdateInfo(Long id);

    //修改商品信息
    void update(Long id, GoodsParam goodsParam);

    //删除商品信息
    void delete(Long id);

    //获取商品详细信息
    GoodsDetail detail(Long id);
}
