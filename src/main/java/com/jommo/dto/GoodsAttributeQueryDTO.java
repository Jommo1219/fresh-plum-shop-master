package com.jommo.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class GoodsAttributeQueryDTO {
    @NotNull
    private Long goodsAttributeCategoryId;
    @NotNull
    private Integer current;
    @NotNull
    private Integer size;
}
