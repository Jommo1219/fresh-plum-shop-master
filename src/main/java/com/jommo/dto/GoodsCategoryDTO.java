package com.jommo.dto;


import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

/**
 * 添加和修改商品分类的请求体
 * @author 不会开发的小虾米
 */
@Data
public class GoodsCategoryDTO {
    @NotNull(groups = Update.class)
    private Long id;
    @Min(0)
    private Long parentId;
    @NotEmpty(message = "分类名称不能为空")
    @Size(min = 2, max = 20, message = "分类名称长度需在2-20个字符之间")
    private String name;
    //默认值为0
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;
    @URL
    private String pic;

    public interface Update extends Default {}
}
