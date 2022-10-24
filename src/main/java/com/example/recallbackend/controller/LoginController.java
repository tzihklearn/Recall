package com.example.recallbackend.controller;

import com.example.recallbackend.Service.LoginService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.PhoneParam;
import com.example.recallbackend.pojo.dto.param.VerificationParam;
import com.example.recallbackend.pojo.dto.result.LoginSuccessResult;
import com.example.recallbackend.pojo.dto.result.LoginVerificationResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/phone-get-sms")
    public CommonResult<String> loginToGetSms(@Valid @RequestBody PhoneParam phoneParam) {
        return loginService.loginToGetSms(phoneParam);
    }


    @PostMapping("/phone-verification-sms")
    public CommonResult<LoginVerificationResult> loginVerification(@Valid @RequestBody VerificationParam verificationParam) {
        return loginService.loginVerification(verificationParam);
    }

    @PostMapping("/phone-token")
    public CommonResult<LoginSuccessResult> loginByToken() {
        return loginService.loginByToken();
    }

}
