package com.example.recallbackend.common;

import lombok.Data;

@Data
public class Constant {
    /**
     * 短信验证码过期时间
     */
    public static final Integer expirationTime = 3;

    /**
     * 密码加密
     */
    public static final String SALT = "*fdsvkljd&SHDFEKfEW03";
}
