package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.domain.UserInfo;
import com.example.recallbackend.pojo.dto.result.LoginSuccessResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {

    Integer selectIdByPhone(String phone);

    UserInfo selectAllByUserId(Integer userId);

    LoginSuccessResult selectLoginByUserId(Integer userId);

    Integer selectUserIdByQRCode(String qrCode);

    int insertAllByUserId(UserInfo userInfo);

    int updateAllByUserId(UserInfo userInfo);

    int updateNameByUserId(Integer userId, String name);

    int updateQRCodeByUserIdInt(Integer userId, String qrCode);
}
