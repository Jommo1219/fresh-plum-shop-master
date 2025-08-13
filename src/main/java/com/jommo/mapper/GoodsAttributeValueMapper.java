package com.jommo.mapper;

import com.jommo.domain.GoodsAttributeValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 不会开发的小虾米
* @description 针对表【goods_attribute_value(商品属性值表)】的数据库操作Mapper
* @createDate 2025-04-16 09:42:14
* @Entity com.jommo.domain.GoodsAttributeValue
*/
public interface GoodsAttributeValueMapper extends BaseMapper<GoodsAttributeValue> {

    void insertList(List<GoodsAttributeValue> goodsAttributeValueList);
}




