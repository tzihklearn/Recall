package com.example.recallbackend.mapper;

import com.example.recallbackend.pojo.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {

    Integer selectIdByPhone(String phone);

    int insertAllByUserId(UserInfo userInfo);

    int updateAllByUserId(UserInfo userInfo);

}
