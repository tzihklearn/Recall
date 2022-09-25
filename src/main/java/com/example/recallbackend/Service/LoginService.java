package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.PhoneParam;
import com.example.recallbackend.pojo.dto.param.VerificationParam;
import com.example.recallbackend.pojo.dto.result.LoginSuccessResult;
import com.example.recallbackend.pojo.dto.result.LoginVerificationResult;

public interface LoginService {

    CommonResult<String> loginToGetSms(PhoneParam phoneParam);

    CommonResult<LoginVerificationResult> loginVerification(VerificationParam verificationParam);

    CommonResult<LoginSuccessResult> loginByToken();

}
