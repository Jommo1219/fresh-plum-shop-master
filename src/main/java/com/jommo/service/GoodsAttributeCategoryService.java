package com.jommo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.domain.GoodsAttributeCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jommo.dto.GoodsAttributeCategoryDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
* @author 不会开发的小虾米
* @description 针对表【goods_attribute_category(产品属性分类表)】的数据库操作Service
* @createDate 2025-04-02 15:19:32
*/
public interface GoodsAttributeCategoryService extends IService<GoodsAttributeCategory> {

    //添加商品属性类型
    void add(GoodsAttributeCategoryDTO categoryDTO);

    //分页查询商品属性类型
    Page<GoodsAttributeCategory> getList(Integer current, Integer size);

    //更新商品属性类型
    void update(GoodsAttributeCategoryDTO categoryDTO);

    //根据分类名查找
    GoodsAttributeCategory findByName(@NotEmpty(message = "属性类型名不能为空") @Size(min = 2, max = 5, message = "属性类型名长度需在5-16个字符之间") String name);

    //删除商品属性类型
    void delete(Long id);
}
