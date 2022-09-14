package com.example.recallbackend.controller;

import com.example.recallbackend.pojo.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tzih
 * @date 2022.09.13
 */
@RestController
@RequestMapping(value = "/user-message", method = RequestMethod.POST)
public class UserMessageController {

    public CommonResult<String> setUserInfo(){
        return null;
    }

}
