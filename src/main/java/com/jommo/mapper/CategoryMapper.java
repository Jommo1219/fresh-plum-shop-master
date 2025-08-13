package com.jommo.mapper;

import com.jommo.domain.GoodsCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jommo.dto.GoodsCategoryWithChildrenItem;

import java.util.List;

/**
* @author 不会开发的小虾米
* @description 针对表【category】的数据库操作Mapper
* @createDate 2025-03-31 12:12:03
* @Entity com.jommo.domain.Category
*/
public interface CategoryMapper extends BaseMapper<GoodsCategory> {

    List<GoodsCategoryWithChildrenItem> listWithChildren();
}




