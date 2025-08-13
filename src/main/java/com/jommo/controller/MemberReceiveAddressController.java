package com.jommo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jommo.common.Result;
import com.jommo.domain.MemberReceiveAddress;
import com.jommo.service.MemberReceiveAddressService;
import com.jommo.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/member/address")
@CrossOrigin
public class MemberReceiveAddressController {

    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;

    //添加
    @PostMapping("/add")
    public Result add(@RequestBody MemberReceiveAddress memberReceiveAddress) {
        memberReceiveAddressService.add(memberReceiveAddress);
        return Result.success();
    }

    //根据用户id查询
    @GetMapping("/list")
    public Result<List<MemberReceiveAddress>> list() {
        List<MemberReceiveAddress> list = memberReceiveAddressService.listByMemberId();
        return Result.success(list);
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        memberReceiveAddressService.removeById(id);
        return Result.success();
    }

    //获取地址详细信息
    @GetMapping("/detail")
    public Result<MemberReceiveAddress> detail(@RequestParam Long id) {
        return Result.success(memberReceiveAddressService.getById(id));
    }

    //修改地址详细信息
    @PutMapping("/update")
    public Result update(@RequestBody MemberReceiveAddress memberReceiveAddress) {
        memberReceiveAddressService.update(memberReceiveAddress);
        return Result.success();
    }
    @PutMapping("/update/defaultStatus/{id}")
    public Result updateDefaultStatus(@PathVariable Long id) {
        memberReceiveAddressService.updateDefaultStatus(id);
        return Result.success();
    }
}
