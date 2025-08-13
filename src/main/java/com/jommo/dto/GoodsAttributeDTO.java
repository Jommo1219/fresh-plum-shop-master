package com.jommo.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class GoodsAttributeDTO {
    @NotNull(groups = Update.class)
    private Long id;
    @NotNull(message = "请选择属性分类")
    private Long goodsAttributeCategoryId;
    @NotEmpty(message = "属性名不能为空")
    @Size(min = 2, max = 10, message = "属性名长度需在2~10个字符之间")
    private String name;
    @NotEmpty(message = "属性可选值列表不能为空")
    private String inputList;
    @NotNull
    private Integer sort;

    public interface Update extends Default {}
}
