package com.example.recallbackend.pojo.dto.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.09.12
 */
@Data
public class VerificationParam {

    @NotBlank
    @Pattern(regexp = "^(1[3,456789])\\d{9}$", message = "电话号不符合规范")
    private String phone;

    @NotBlank
    @Pattern(regexp = "^[0-9]{4}$", message = "验证码不符合规范")
    private String verificationCode;

}
