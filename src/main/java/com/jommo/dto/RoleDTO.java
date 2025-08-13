package com.jommo.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class RoleDTO {
    @NotNull(groups = RoleDTO.Update.class)
    private Long id;
    @NotEmpty(message = "请输入角色名称")
    private String name;
    private String description;
    private Integer status;

    public interface Update extends Default {};
}
