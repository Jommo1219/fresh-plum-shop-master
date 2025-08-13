package com.jommo.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class MemberDTO {

    @NotEmpty(message = "请输入用户名")
    private String username;
    @NotEmpty(message = "请输入密码")
    private String password;
    @NotEmpty(groups =Register.class, message = "请输入确认密码")
    private String rePassword;
    @NotEmpty(groups = Register.class, message = "请输入手机号码")
    @Pattern(groups = Register.class, regexp = "^1(?:3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$")
    private String phone;

    public interface Register extends Default {
    }

}
