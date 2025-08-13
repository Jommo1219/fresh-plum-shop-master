package com.jommo.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

/**
 * @author 不会开发的小虾米
 */
@Data
public class AdvertiseDTO {

    @NotNull(groups = Update.class)
    private Long id;
    @NotEmpty
    private String name;
    private Integer status;
    @URL
    private String pic;
    private String note;

    public interface Update extends Default {}
}
