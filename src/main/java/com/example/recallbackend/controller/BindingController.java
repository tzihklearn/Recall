package com.example.recallbackend.controller;

import com.example.recallbackend.Service.BindingService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.ChildNameParam;
import com.example.recallbackend.pojo.dto.param.NameParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tzih
 * @date 2022.09.19
 */
@RestController
@RequestMapping(value = "/parent/binding", method = RequestMethod.POST)
public class BindingController {

    @Resource
    private BindingService bindingService;

    @PostMapping("/change-name")
    public CommonResult<String> parentChangeName(@RequestBody NameParam nameParam) {
        return bindingService.parentChangeName(nameParam);
    }

    @PostMapping("/child-name")
    public CommonResult<String> setChildName(@RequestBody ChildNameParam childNameParam) {
        return bindingService.setChildName(childNameParam);
    }

    @PostMapping("/unbinding")
    public CommonResult<String> unbinding(@RequestBody ChildNameParam childNameParam) {
        return bindingService.unbinding(childNameParam);
    }

}
