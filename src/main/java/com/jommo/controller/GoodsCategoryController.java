package com.jommo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.domain.GoodsCategory;
import com.jommo.common.Result;
import com.jommo.dto.GoodsCategoryDTO;
import com.jommo.dto.GoodsCategoryWithChildrenItem;
import com.jommo.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/goodsCate")
@CrossOrigin
public class GoodsCategoryController {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    //添加
    @PostMapping
    public Result add(@RequestBody @Validated GoodsCategoryDTO goodsCategoryDTO) {
        //根据分类名查询
        GoodsCategory c = goodsCategoryService.findByName(goodsCategoryDTO.getName());
        if (c != null) {
            return Result.error("该分类已存在");
        }
        //添加
        goodsCategoryService.add(goodsCategoryDTO);
        return Result.success();
    }

    //分页查询
    @GetMapping
    public Result<Page<GoodsCategory>> getList(Long parentId, Integer current, Integer size) {
        Page<GoodsCategory> page = goodsCategoryService.getList(parentId, current, size);
        return Result.success(page);
    }

    //更新
    @PutMapping
    public Result update(@RequestBody @Validated(GoodsCategoryDTO.Update.class) GoodsCategoryDTO goodsCategoryDTO) {
        LambdaQueryWrapper<GoodsCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCategory::getName, goodsCategoryDTO.getName());
        queryWrapper.ne(GoodsCategory::getId, goodsCategoryDTO.getId());
        GoodsCategory goodsCategory = goodsCategoryService.getOne(queryWrapper);
        if (goodsCategory == null) {
            goodsCategoryService.update(goodsCategoryDTO);
            return Result.success();
        }
        return Result.error("该分类已存在");
    }

    //删除
    @DeleteMapping
    public Result delete(Long id) {
        goodsCategoryService.delete(id);
        return Result.success();
    }

    //获取所有父类分类
    @GetMapping("/getAllCateParentList")
    public Result<List<GoodsCategory>> getAllCateParentList() {
        LambdaQueryWrapper<GoodsCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCategory::getParentId, 0L);
        List<GoodsCategory> list = goodsCategoryService.list(queryWrapper);
        return Result.success(list);
    }

    //获取所有分类及其子分类
    @GetMapping("/listWithChildren")
    public Result<List<GoodsCategoryWithChildrenItem>> listWithChildren() {
        List<GoodsCategoryWithChildrenItem> list = goodsCategoryService.listWithChildren();
        return Result.success(list);
    }
}
