package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.domain.UserInfo;
import com.example.recallbackend.pojo.dto.result.LoginSuccessResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {

    Integer selectIdByPhone(String phone);

    LoginSuccessResult selectLoginByUserId(Integer userId);

    int insertAllByUserId(UserInfo userInfo);

    int updateAllByUserId(UserInfo userInfo);

    int updateNameByUserId(Integer userId, String name);


}
