package com.jommo.mapper;

import com.jommo.domain.GoodsAttribute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
* @author 不会开发的小虾米
* @description 针对表【goods_attribute(商品属性表)】的数据库操作Mapper
* @createDate 2025-04-08 14:40:55
* @Entity com.jommo.domain.GoodsAttribute
*/
public interface GoodsAttributeMapper extends BaseMapper<GoodsAttribute> {

    List<GoodsAttribute> selectByGoodsAttributeCategoryIdForPage(Map<String, Object> map);

    Integer selectCountByGoodsAttributeCategoryId(Long goodsAttributeCategoryId);
}




