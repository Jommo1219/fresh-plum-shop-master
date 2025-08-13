package com.jommo.dto;


import com.jommo.domain.Goods;
import com.jommo.domain.GoodsAttributeValue;
import com.jommo.domain.SkuStock;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@Data
@EqualsAndHashCode
public class GoodsParam extends Goods {
    //商品的sku库存信息
    private List<SkuStock> skuStockList;
    //商品参数属性
    private List<GoodsAttributeValue> goodsAttributeValueList;
}
