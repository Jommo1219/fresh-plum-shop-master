package com.jommo.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class AdminUpdatePwdDTO {

    @NotEmpty(message = "请输入原密码")
    private String oldPassword;
    @NotEmpty(message = "请输入新密码")
    @Size(min = 8, max = 16, message = "密码长度需在8-16个字符之间")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "新密码必须包含大小写字母、数字和特殊字符")
    private String newPassword;
    @NotEmpty(message = "请再次输入新密码")
    private String confirmPassword;

}
