package com.example.recallbackend.controller;

import com.example.recallbackend.Service.LoginService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.PhoneParam;
import com.example.recallbackend.pojo.dto.param.VerificationParam;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/login", method = RequestMethod.POST)
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/phone-get-sms")
    public CommonResult<String> loginToGetSms(@RequestBody PhoneParam phoneParam) {
        return loginService.loginToGetSms(phoneParam);
    }

    @PostMapping("/phone-verification-sms")
    public CommonResult<String> loginVerification(@RequestBody VerificationParam verificationParam) {
        return loginService.loginVerification(verificationParam);
    }

    @PostMapping("/phone-token")
    public CommonResult<String> loginByToken() {
        return loginService.loginByToken();
    }

}
