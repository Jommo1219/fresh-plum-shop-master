package com.jommo.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jommo.common.Result;
import com.jommo.domain.SkuStock;
import com.jommo.service.SkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/skuStock")
@CrossOrigin
public class SkuStockController {

    @Autowired
    private SkuStockService skuStockService;

    //根据商品ID获取sku库存
    @GetMapping("/{goodsId}")
    public Result<List<SkuStock>> getList(@PathVariable Long goodsId) {
        List<SkuStock> list = skuStockService.lambdaQuery().eq(SkuStock::getGoodsId, goodsId).list();
        return Result.success(list);
    }

    //批量更新sku库存信息
    @PostMapping("/update/{goodsId}")
    public Result update(@PathVariable Long goodsId, @RequestBody List<SkuStock> skuStockList) {
        skuStockList.forEach(skuStock -> {
            skuStockService.lambdaUpdate().eq(SkuStock::getGoodsId, goodsId).update(skuStock);
        });
        return Result.success();
    }

}
