package com.jommo.dto;


import com.jommo.domain.Goods;
import com.jommo.domain.GoodsAttribute;
import com.jommo.domain.GoodsAttributeValue;
import com.jommo.domain.SkuStock;
import lombok.Data;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@Data
public class GoodsDetail {
    //商品信息
    private Goods goods;
    //商品属性
    private List<GoodsAttribute> goodsAttributeList;
    //商品属性值
    private List<GoodsAttributeValue> goodsAttributeValueList;
    //sku库存信息
    private List<SkuStock> skuStockList;
}
