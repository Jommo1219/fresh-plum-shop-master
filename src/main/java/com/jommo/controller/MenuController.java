package com.jommo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.common.Result;
import com.jommo.domain.Menu;
import com.jommo.domain.MenuNode;
import com.jommo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    //添加
    @PostMapping
    public Result add(@RequestBody Menu menu) {
        if (menu.getParentId() != 0) {
            menu.setLevel(1);
        } else {
            menu.setLevel(0);
        }
        menuService.save(menu);
        return Result.success();
    }

    //分页查询
    @GetMapping
    public Result<Page<Menu>> getMenuList(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                          @RequestParam(value = "size", defaultValue = "5") Integer size,
                                          @RequestParam(value = "parentId", defaultValue = "0") Long parentId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, parentId);
        Page<Menu> page = menuService.page(new Page<>(current, size), queryWrapper);
        return Result.success(page);
    }

    //获取所有父级菜单
    @GetMapping("/getParentMenuList")
    public Result<List<Menu>> getParentMenuList() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, 0L);
        List<Menu> menuList = menuService.list(queryWrapper);
        return Result.success(menuList);
    }

    //修改
    @PutMapping
    public Result update(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return Result.success();
    }

    //删除
    @DeleteMapping
    public Result delete(@RequestParam("id") Long id) {
        menuService.removeById(id);
        return Result.success();
    }

    //修改显示状态
    @PostMapping("/update/isHidden")
    public Result updateIsHidden(@RequestParam("id") Long id) {
        Menu menu = menuService.getById(id);
        if (menu == null) {
            return Result.error("操作异常");
        }
        LambdaUpdateWrapper<Menu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Menu::getId, id);
        updateWrapper.set(Menu::getIsHidden, menu.getIsHidden() == 1 ? 0 : 1);
        menuService.update(updateWrapper);
        return Result.success();
    }

    //获取所有菜单列表
    @GetMapping("/treeList")
    public Result<List<MenuNode>> treeList() {
        List<MenuNode> list = menuService.treeList();
        return Result.success(list);
    }
}
