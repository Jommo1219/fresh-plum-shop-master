package com.jommo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.common.Result;
import com.jommo.common.Status;
import com.jommo.domain.OrderReturnReason;
import com.jommo.dto.OrderReturnReasonDTO;
import com.jommo.service.OrderReturnReasonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/returnReason")
@CrossOrigin
public class OrderReturnReasonController {
    @Autowired
    private OrderReturnReasonService orderReturnReasonService;

    //添加
    @PostMapping
    public Result add(@RequestBody @Validated OrderReturnReasonDTO reasonDTO) {
        OrderReturnReason existedReason = orderReturnReasonService.getByName(reasonDTO.getName());
        if (!Objects.isNull(existedReason)) {
            return Result.error("该原因类型已存在");
        }
        OrderReturnReason newReason = new OrderReturnReason();
        BeanUtils.copyProperties(reasonDTO, newReason);
        orderReturnReasonService.save(newReason);
        return Result.success();
    }

    //分页查询
    @GetMapping
    public Result<Page<OrderReturnReason>> getList(Integer current, Integer size) {
        Page<OrderReturnReason> page = orderReturnReasonService.page(new Page<>(current, size));
        return Result.success(page);
    }

    //修改
    @PutMapping
    public Result update(@RequestBody @Validated(OrderReturnReasonDTO.Update.class) OrderReturnReasonDTO reasonDTO) {
        OrderReturnReason existedReason = orderReturnReasonService.getByName(reasonDTO.getName());
        if (!Objects.isNull(existedReason) && !existedReason.getId().equals(reasonDTO.getId())) {
            return Result.error("该原因类型已存在");
        }
        OrderReturnReason reason = new OrderReturnReason();
        BeanUtils.copyProperties(reasonDTO, reason);
        orderReturnReasonService.updateById(reason);
        return Result.success();
    }

    //删除
    @DeleteMapping
    public Result delete(@RequestParam Long id) {
        orderReturnReasonService.removeById(id);
        return Result.success();
    }

    //修改状态
    @PutMapping("/updateStatus")
    public Result updateStatus(@RequestParam Long id) {
        OrderReturnReason currentReason = orderReturnReasonService.getById(id);
        OrderReturnReason reason = new OrderReturnReason();
        reason.setId(id);
        reason.setStatus(Objects.equals(currentReason.getStatus(), Status.DISABLED) ? Status.ENABLED : Status.DISABLED);
        orderReturnReasonService.updateById(reason);
        return Result.success();
    }
}
