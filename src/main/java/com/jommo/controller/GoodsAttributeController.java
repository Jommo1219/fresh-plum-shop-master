package com.jommo.controller;


import com.jommo.common.Page;
import com.jommo.common.Result;
import com.jommo.domain.GoodsAttribute;
import com.jommo.dto.GoodsAttributeDTO;
import com.jommo.service.GoodsAttributeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/goodsAttr")
@CrossOrigin
public class GoodsAttributeController {
    @Autowired
    private GoodsAttributeService goodsAttributeService;

    //添加
    @PostMapping
    public Result add(@RequestBody @Validated GoodsAttributeDTO attributeDTO) {
        //判断商品属性名是否存在
        GoodsAttribute attribute = goodsAttributeService.findByName(attributeDTO.getName(), attributeDTO.getGoodsAttributeCategoryId());
        if (!Objects.isNull(attribute)) {
            return Result.error("该属性名已存在");
        }
        goodsAttributeService.add(attributeDTO);
        return Result.success();
    }

    //分页查询
    @GetMapping("/list/{goodsAttributeCategoryId}")
    public Result<Page<GoodsAttribute>> getList(@PathVariable Long goodsAttributeCategoryId,@RequestParam(value = "current",defaultValue = "1") Integer current,@RequestParam(value = "size",defaultValue = "8") Integer size) {
        Page<GoodsAttribute> page = goodsAttributeService.getList(goodsAttributeCategoryId, current, size);
        return Result.success(page);
    }

    //修改
    @PutMapping
    public Result update(@RequestBody @Validated(GoodsAttributeDTO.Update.class) GoodsAttributeDTO attributeDTO) {
        goodsAttributeService.update(attributeDTO);
        return Result.success();
    }

    //删除
    @DeleteMapping
    public Result delete(@RequestParam @Validated @NotNull Long id) {
        goodsAttributeService.delete(id);
        return Result.success();
    }

}
