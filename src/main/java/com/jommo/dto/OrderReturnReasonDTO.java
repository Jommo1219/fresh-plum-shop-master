package com.jommo.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class OrderReturnReasonDTO {

    @NotNull(groups = Update.class)
    private Long id;
    @NotEmpty(message = "请输入原因类型")
    private String name;
    private Integer status;
    private Integer sort;

    public interface Update extends Default {};
}
