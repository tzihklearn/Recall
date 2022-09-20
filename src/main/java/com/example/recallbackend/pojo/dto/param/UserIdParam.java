package com.example.recallbackend.pojo.dto.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tzih
 * @date 2022.09.20
 */
@Data
public class UserIdParam {

    @NotNull
    Integer userId;

}
