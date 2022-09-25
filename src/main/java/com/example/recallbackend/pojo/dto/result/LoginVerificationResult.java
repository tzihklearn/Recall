package com.example.recallbackend.pojo.dto.result;

import lombok.Data;

/**
 * @author tzih
 * @date 2022.09.23
 */
@Data
public class LoginVerificationResult {

    private Integer userId;

    private String name;

    private Integer roleModel;

    private String token;
}
