package com.jommo.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 不会开发的小虾米
 */
@Data
@EqualsAndHashCode
public class GoodsQueryParam {
    //上架状态
    private Integer publishStatus;
    //商品名称模糊关键字
    private String keyword;
    //商品货号
    private String goodsSn;
    //商品分类编号
    private Long goodsCategoryId;
}
