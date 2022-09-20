package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.BindingService;
import com.example.recallbackend.mapper.UserInfoMapper;
import com.example.recallbackend.mapper.UserRelationMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.ChildNameParam;
import com.example.recallbackend.pojo.dto.param.NameParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tzih
 * @date 2022.09.19
 */
@Service
public class BindingServiceImpl implements BindingService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserRelationMapper userRelationMapper;

    @Override
    public CommonResult<String> parentChangeName(NameParam nameParam) {

//        Integer userId = Integer.valueOf(request.getHeader("jwt"));

        int i = userInfoMapper.updateNameByUserId(nameParam.getUserId(), nameParam.getName());

        if (i == 1) {
            return CommonResult.success("更改用户名成功");
        }
        else {
            return CommonResult.fail("更改用户名失败");
        }

    }

    @Override
    public CommonResult<String> setChildName(ChildNameParam childNameParam) {

//        Integer userId = Integer.valueOf(request.getHeader("jwt"));

        int i = userRelationMapper.updateChildName(childNameParam.getParentId(), childNameParam.getChildId(), childNameParam.getName());
        if (i == 1) {
            return CommonResult.success("更新成功");
        }
        else {
            return CommonResult.fail("更新失败");
        }

    }

    @Override
    public CommonResult<String> unbinding(ChildNameParam childNameParam) {

        int i = userRelationMapper.deleteRelation(childNameParam.getParentId(), childNameParam.getChildId());

        if (i == 0) {
            return CommonResult.success("解绑成功");
        }
        else {
            return CommonResult.fail("解绑失败");
        }
    }
}
