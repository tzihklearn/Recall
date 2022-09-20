package com.example.recallbackend.pojo.domain;

import lombok.Data;

/**
 * @author tzih
 * @date 2022.09.12
 */
@Data
public class UserInfo {
    Integer userId;
    String loginId;
    String name;
    String phone;
    //Inner
    Integer roleModel;
    Integer state;
    String token;
    String QRCode;
    String birthday;
}
