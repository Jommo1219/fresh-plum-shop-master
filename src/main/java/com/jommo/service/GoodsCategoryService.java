package com.jommo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jommo.domain.GoodsCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jommo.dto.GoodsCategoryDTO;
import com.jommo.dto.GoodsCategoryWithChildrenItem;

import java.util.List;

/**
 * @author 不会开发的小虾米
 * @description 针对表【category】的数据库操作Service
 * @createDate 2025-03-31 12:12:03
 */
public interface GoodsCategoryService extends IService<GoodsCategory> {

    //添加商品分类
    void add(GoodsCategoryDTO goodsCategoryDTO);

    //根据分类名查询
    GoodsCategory findByName(String name);

    //分页查询商品分类
    Page<GoodsCategory> getList(Long parentId, Integer current, Integer size);

    //更新商品分类
    void update(GoodsCategoryDTO goodsCategoryDTO);

    //删除商品分类
    void delete(Long id);

    //获取商品分类及其子分类
    List<GoodsCategoryWithChildrenItem> listWithChildren();
}
