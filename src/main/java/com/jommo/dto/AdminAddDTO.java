package com.jommo.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 添加后台用户的请求体
 * @author 不会开发的小虾米
 */
@Data
public class AdminAddDTO {

    @NotEmpty(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    @Size(min = 5, max = 16, message = "用户名长度需在5-16个字符之间")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, message = "密码长度不能低于8位")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;\"'<>,.?/\\\\|~`-]).*$",
            message = "密码必须包含大小写字母、数字和特殊字符")
    private String password;

    @NotEmpty(message = "请输入邮箱地址")
    @Email(message = "请输入合法的邮箱地址")
    private String email;

    @NotEmpty(message = "请输入昵称")
    private String nickname;

    @NotNull(message = "请选择用户启用状态")
    private Integer status;
}
