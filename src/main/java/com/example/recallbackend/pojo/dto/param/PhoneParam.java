package com.example.recallbackend.pojo.dto.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PhoneParam {
    @NotBlank
    @Pattern(regexp = "^(1[3,456789])\\d{9}$", message = "电话号不符合规范")
    private String phone;
}
