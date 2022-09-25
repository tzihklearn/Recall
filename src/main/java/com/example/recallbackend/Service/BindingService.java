package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.ChangeNameParam;
import com.example.recallbackend.pojo.dto.param.NameParam;
import com.example.recallbackend.pojo.dto.param.RelationParam;
import com.example.recallbackend.pojo.dto.param.UserIdParam;
import com.example.recallbackend.pojo.dto.result.UserResult;

import java.util.List;

public interface BindingService {

    CommonResult<UserResult> getName(Integer userId);
    CommonResult<List<UserResult>> getAllBinding(Integer parentId);

    CommonResult<String> parentChangeName(NameParam nameParam);

    CommonResult<String> setChildName(ChangeNameParam changeNameParam);

    CommonResult<String> unbinding(RelationParam relationParam);

    CommonResult<String> creatQRCode(UserIdParam userIdParam);

}
