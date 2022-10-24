package com.example.recallbackend.pojo.dto.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tzih
 * @date 2022.09.25
 */
@Data
public class SubmitVideoParam {

    @NotNull
    Integer userId;

    @NotBlank
    String data;

}
