package com.jommo.service;

import com.jommo.common.Page;
import com.jommo.domain.GoodsAttribute;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jommo.dto.GoodsAttributeDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
* @author 不会开发的小虾米
* @description 针对表【goods_attribute(商品属性表)】的数据库操作Service
* @createDate 2025-04-02 15:59:48
*/
public interface GoodsAttributeService extends IService<GoodsAttribute> {

    //根据属性名查找
    GoodsAttribute findByName(@NotEmpty(message = "属性名不能为空") String name, @NotNull Long goodsAttributeCategoryId);

    //添加商品属性
    void add(GoodsAttributeDTO attributeDTO);

    //分页查询商品属性
    Page<GoodsAttribute> getList(Long goodsAttributeCategoryId, Integer current, Integer size);

    //更新商品属性
    void update(GoodsAttributeDTO attributeDTO);

    //删除商品属性
    void delete(@NotNull Long id);
}
