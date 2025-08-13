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
public class GoodsAttributeCategoryDTO {

    @NotNull(groups = Update.class)
    private Long id;
    @NotEmpty(message = "属性类型名不能为空")
    @Size(min = 2, max = 10, message = "属性类型名长度需在2-10个字符之间")
    private String name;

    public interface Update extends Default {}
}
