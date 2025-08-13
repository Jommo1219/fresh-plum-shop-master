package com.jommo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.common.Result;
import com.jommo.domain.GoodsAttributeCategory;
import com.jommo.dto.GoodsAttributeCategoryDTO;
import com.jommo.service.GoodsAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/goodsAttrCate")
@CrossOrigin
public class GoodsAttributeCategoryController {

    @Autowired
    private GoodsAttributeCategoryService goodsAttributeCategoryService;

    //添加
    @PostMapping
    public Result add(@RequestBody @Validated GoodsAttributeCategoryDTO categoryDTO) {
        //判断分类名是否已存在
        GoodsAttributeCategory category = goodsAttributeCategoryService.findByName(categoryDTO.getName());
        if(!Objects.isNull(category)){
            return Result.error("该属性分类名已存在");
        }
        goodsAttributeCategoryService.add(categoryDTO);
        return Result.success();
    }

    //分页查询商品属性分类
    @GetMapping
    public Result<Page<GoodsAttributeCategory>> getList(Integer current, Integer size) {
        Page<GoodsAttributeCategory> page = goodsAttributeCategoryService.getList(current, size);
        return Result.success(page);
    }

    //更新商品属性分类
    @PutMapping
    public Result update(@RequestBody @Validated GoodsAttributeCategoryDTO categoryDTO) {
        goodsAttributeCategoryService.update(categoryDTO);
        return Result.success();
    }

    //删除
    @DeleteMapping
    public Result delete(@RequestParam Long id) {
        goodsAttributeCategoryService.delete(id);
        return Result.success();
    }

    //获取所有属性类型
    @GetMapping("/getAll")
    public Result<List<GoodsAttributeCategory>> getAll() {
        List<GoodsAttributeCategory> list = goodsAttributeCategoryService.list();
        return Result.success(list);
    }
}
