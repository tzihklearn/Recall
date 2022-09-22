package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.ChangeNameParam;
import com.example.recallbackend.pojo.dto.param.MemorandumParam;
import com.example.recallbackend.pojo.dto.param.NameParam;
import com.example.recallbackend.pojo.dto.param.QRCodeParam;
import com.example.recallbackend.pojo.dto.result.UserResult;

import java.util.List;

public interface ChildHomepageService {

    CommonResult<UserResult> getName(Integer userId);

    CommonResult<String> changeUserName(NameParam nameParam);

    CommonResult<String> binding(QRCodeParam qrCodeParam);

    CommonResult<List<UserResult>> getAllBinding(Integer userId);

    CommonResult<String> setParentName(ChangeNameParam changeNameParam);

    CommonResult<String> unbinding(ChangeNameParam changeNameParam);

}
