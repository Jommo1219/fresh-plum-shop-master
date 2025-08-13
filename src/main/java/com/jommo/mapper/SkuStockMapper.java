package com.jommo.mapper;

import com.jommo.domain.SkuStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 不会开发的小虾米
* @description 针对表【sku_stock】的数据库操作Mapper
* @createDate 2025-04-29 22:03:14
* @Entity com.jommo.domain.SkuStock
*/
public interface SkuStockMapper extends BaseMapper<SkuStock> {

    void insertList(List<SkuStock> skuStockList);


    int reduceStock(Long goodsSkuId, Integer goodsQuantity);

    int releaseStockBySkuId(Long goodsSkuId, Integer goodsQuantity);

    void recoverStock(Long goodsSkuId, Integer goodsQuantity);

    void updateSale(Long goodsSkuId, Integer goodsQuantity);
}




