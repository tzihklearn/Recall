package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.PhoneParam;
import com.example.recallbackend.pojo.dto.param.VerificationParam;

public interface LoginService {

    CommonResult<String> loginToGetSms(PhoneParam phoneParam);

    CommonResult<String> loginVerification(VerificationParam verificationParam);

    CommonResult<String> loginByToken();

}
