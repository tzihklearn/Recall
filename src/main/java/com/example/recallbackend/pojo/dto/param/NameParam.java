package com.example.recallbackend.pojo.dto.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tzih
 * @date 2022.09.19
 */
@Data
public class NameParam {

    @NotNull
    private Integer userId;

//    @Pattern(regexp = "", message = "姓名不符合规范")
    @NotNull
    private String name;

}
