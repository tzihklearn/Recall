package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.PhoneParam;
import com.example.recallbackend.pojo.dto.param.VerificationParam;
import com.example.recallbackend.pojo.dto.result.LoginSuccessResult;

public interface LoginService {

    CommonResult<String> loginToGetSms(PhoneParam phoneParam);

    CommonResult<LoginSuccessResult> loginVerification(VerificationParam verificationParam);

    CommonResult<LoginSuccessResult> loginByToken();

}
