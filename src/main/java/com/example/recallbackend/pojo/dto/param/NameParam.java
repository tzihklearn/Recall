package com.example.recallbackend.pojo.dto.param;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tzih
 * @date 2022.09.19
 */
@Data
public class NameParam {

    @NotNull
    private Integer userId;

//    @Pattern(regexp = "[\\u4e00-\\u9fa5]", message = "姓名不符合规范")
    @NotBlank
    private String name;

}
