package com.jommo.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class AdminUpdateDTO {
    @NotNull
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String username;
}
