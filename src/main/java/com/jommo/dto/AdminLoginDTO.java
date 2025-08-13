package com.jommo.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class AdminLoginDTO {

    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;

}
