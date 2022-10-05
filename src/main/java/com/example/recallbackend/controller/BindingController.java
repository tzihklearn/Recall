package com.example.recallbackend.controller;

import com.example.recallbackend.Service.BindingService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.ChangeNameParam;
import com.example.recallbackend.pojo.dto.param.NameParam;
import com.example.recallbackend.pojo.dto.param.RelationParam;
import com.example.recallbackend.pojo.dto.param.UserIdParam;
import com.example.recallbackend.pojo.dto.result.UserResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.19
 */
@RestController
@RequestMapping(value = "/parent/binding")
public class BindingController {

    @Resource
    private BindingService bindingService;
    
    @GetMapping("/get-name")
    public CommonResult<UserResult> getName(@NotNull Integer userId) {
        return bindingService.getName(userId);
    }

    @GetMapping("/all")
    public CommonResult<List<UserResult>> getAllBinding(@NotNull Integer parentId) {
        return bindingService.getAllBinding(parentId);
    }

    @PostMapping("/change-name")
    public CommonResult<String> parentChangeName(@RequestBody NameParam nameParam) {
        return bindingService.parentChangeName(nameParam);
    }

    @PostMapping("/change-child-name")
    public CommonResult<String> setChildName(@RequestBody ChangeNameParam changeNameParam) {
        return bindingService.setChildName(changeNameParam);
    }

    @PostMapping("/unbinding")
    public CommonResult<String> unbinding(@RequestBody RelationParam relationParam) {
        return bindingService.unbinding(relationParam);
    }

    @PostMapping("/creat-qrcode")
    public CommonResult<String> creatQRCode(@RequestBody UserIdParam userIdParam) {
        return bindingService.creatQRCode(userIdParam);
    }

}
