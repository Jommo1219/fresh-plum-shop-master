package com.jommo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.common.Status;
import com.jommo.domain.Goods;
import com.jommo.dto.*;
import com.jommo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.jommo.common.Result;

import java.util.List;


/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    //添加
    @PostMapping("/add")
    @Transactional
    public Result add(@RequestBody GoodsParam goodsParam) {
        goodsService.add(goodsParam);
        return Result.success();
    }

    //分页查询
    @GetMapping
    public Result<Page<Goods>> getList(GoodsQueryParam param,
                                       @RequestParam(value = "current", defaultValue = "1") Integer current,
                                       @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Page<Goods> page = goodsService.list(param, current, size);
        return Result.success(page);
    }

    //修改商品状态
    @PutMapping("/updatePublishStatus")
    public Result updatePublishStatus(@RequestBody GoodsStatusDTO dto) {
        goodsService.lambdaUpdate()
                .eq(Goods::getId, dto.getId())
                .set(Goods::getPublishStatus, dto.getPublishStatus())
                .update();
        return Result.success();
    }

    //获取商品修改信息
    @GetMapping("/{id}")
    public Result<GoodsResult> get(@PathVariable Long id) {
        return Result.success(goodsService.getUpdateInfo(id));
    }

    //修改
    @PutMapping("/update/{id}")
    @Transactional
    public Result update(@PathVariable Long id, @RequestBody GoodsParam goodsParam) {
        goodsService.update(id, goodsParam);
        return Result.success();
    }

    //删除
    @DeleteMapping("/{id}")
    @Transactional
    public Result delete(@PathVariable Long id) {
        goodsService.delete(id);
        return Result.success();
    }

    //获取新鲜商品列表
    @GetMapping("/getNewList")
    public Result<List<Goods>> getNewList() {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Goods::getCreateTime);
        return Result.success(goodsService.list(new Page<>(1, 3), queryWrapper));
    }

    //获取推荐商品
    @GetMapping("/getRecommendList")
    public Result<List<Goods>> getRecommendList() {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Goods::getSale);
        return Result.success(goodsService.list(new Page<>(1, 4), queryWrapper));
    }

    //根据商品分类id获取商品列表
    @GetMapping("/getListByCategoryId")
    public Result<List<Goods>> getListByCategoryId(@RequestParam Long categoryId) {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getGoodsCategoryId, categoryId);
        queryWrapper.eq(Goods::getPublishStatus, Status.ENABLED);
        return Result.success(goodsService.list(queryWrapper));
    }

    //获取商品详情
    @GetMapping("/detail")
    public Result<GoodsDetail> getDetail(@RequestParam Long id) {
        return Result.success(goodsService.detail(id));
    }

}
