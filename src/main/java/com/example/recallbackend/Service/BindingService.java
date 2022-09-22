package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.ChildNameParam;
import com.example.recallbackend.pojo.dto.param.NameParam;
import com.example.recallbackend.pojo.dto.param.UserIdParam;

import java.util.List;

public interface BindingService {

    CommonResult<List<String>> getAllBinding(Integer parentId);

    CommonResult<String> parentChangeName(NameParam nameParam);

    CommonResult<String> setChildName(ChildNameParam childNameParam);

    CommonResult<String> unbinding(ChildNameParam childNameParam);

    CommonResult<String> creatQRCode(UserIdParam userIdParam);

}
