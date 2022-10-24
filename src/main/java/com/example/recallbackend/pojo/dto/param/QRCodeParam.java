package com.example.recallbackend.pojo.dto.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tzih
 * @date 2022.09.13
 */
@Data
public class QRCodeParam {

    @NotNull
    Integer userId;

    @NotBlank
    String key;

}
