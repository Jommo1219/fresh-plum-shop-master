package com.jommo.dto;


import com.jommo.domain.GoodsCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsCategoryWithChildrenItem extends GoodsCategory {

    private List<GoodsCategory> children;

}
