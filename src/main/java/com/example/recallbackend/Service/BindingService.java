package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.ChildNameParam;
import com.example.recallbackend.pojo.dto.param.NameParam;

public interface BindingService {

    CommonResult<String> parentChangeName(NameParam nameParam);

    CommonResult<String> setChildName(ChildNameParam childNameParam);

    CommonResult<String> unbinding(ChildNameParam childNameParq);

}
