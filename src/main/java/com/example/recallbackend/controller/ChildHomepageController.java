package com.example.recallbackend.controller;

import com.example.recallbackend.Service.ChildHomepageService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.MemorandumParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tzih
 * @date 2022.09.21
 */
@RestController
@RequestMapping("/child/homepage")
public class ChildHomepageController {

    @Resource
    private ChildHomepageService childHomepageService;

    @PostMapping("/send-memorandum")
    public CommonResult<String> sendMemorandum(@RequestBody MemorandumParam memorandumParam) {
        return childHomepageService.sendMemorandum(memorandumParam);
    }

}
