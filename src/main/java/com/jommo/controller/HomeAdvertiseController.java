package com.jommo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.common.Result;
import com.jommo.common.Status;
import com.jommo.domain.HomeAdvertise;
import com.jommo.dto.AdvertiseDTO;
import com.jommo.service.HomeAdvertiseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/advertise")
@CrossOrigin
public class HomeAdvertiseController {

    @Autowired
    private HomeAdvertiseService homeAdvertiseService;

    //添加
    @PostMapping
    public Result add(@RequestBody @Validated AdvertiseDTO advertiseDTO) {
        HomeAdvertise homeAdvertise = new HomeAdvertise();
        BeanUtils.copyProperties(advertiseDTO, homeAdvertise);
        homeAdvertiseService.save(homeAdvertise);
        return Result.success();
    }

    //分页查询
    @GetMapping
    public Result<Page<HomeAdvertise>> getList(Integer current, Integer size, String name) {
        LambdaQueryWrapper<HomeAdvertise> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(HomeAdvertise::getName, name);
        Page<HomeAdvertise> page = homeAdvertiseService.page(new Page<>(current, size), queryWrapper);
        return Result.success(page);
    }

    //修改
    @PutMapping
    public Result update(@RequestBody @Validated(AdvertiseDTO.Update.class) AdvertiseDTO advertiseDTO) {
        HomeAdvertise homeAdvertise = new HomeAdvertise();
        BeanUtils.copyProperties(advertiseDTO, homeAdvertise);
        homeAdvertiseService.updateById(homeAdvertise);
        return Result.success();
    }

    //删除
    @DeleteMapping
    public Result delete(@RequestParam Long id) {
        homeAdvertiseService.removeById(id);
        return Result.success();
    }

    //修改上线/下线
    @PutMapping("/updateStatus")
    public Result updateStatus(@RequestParam Long id) {
        HomeAdvertise advertise = homeAdvertiseService.getById(id);
        LambdaUpdateWrapper<HomeAdvertise> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(HomeAdvertise::getId, id).set(HomeAdvertise::getStatus, advertise.getStatus() == 1 ? 0 : 1);
        homeAdvertiseService.update(updateWrapper);
        return Result.success();
    }

    //获取所有启用的广告
    @GetMapping("/getAll")
    public Result<List<HomeAdvertise>> getAll() {
        LambdaQueryWrapper<HomeAdvertise> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HomeAdvertise::getStatus, Status.ENABLED);
        return Result.success(homeAdvertiseService.list(queryWrapper));
    }
}
