package com.example.recallbackend.controller;

import com.example.recallbackend.Service.ChildHomepageService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.*;
import com.example.recallbackend.pojo.dto.result.AnniversaryResult;
import com.example.recallbackend.pojo.dto.result.UserResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.21
 */
@RestController
@RequestMapping(value = "/child/homepage", method = {RequestMethod.GET, RequestMethod.POST})
public class ChildHomepageController {

    @Resource
    private ChildHomepageService childHomepageService;

    @GetMapping("/get-name")
    public CommonResult<UserResult> getName(@NotNull Integer userId) {
        return childHomepageService.getName(userId);
    }

    @PostMapping("/change-user-name")
    public CommonResult<String> changeUserName(@RequestBody NameParam nameParam) {
        return childHomepageService.changeUserName(nameParam);
    }

    @PostMapping("/binding")
    public CommonResult<String> binding(@RequestBody QRCodeParam qrCodeParam) {
        return childHomepageService.binding(qrCodeParam);
    }

    @GetMapping("/all")
    public CommonResult<List<UserResult>> getAllBinding(@NotNull Integer userId) {
        return childHomepageService.getAllBinding(userId);
    }

    @PostMapping("/change-parent-name")
    public CommonResult<String> setParentName(@RequestBody ChangeNameParam changeNameParam) {
        return childHomepageService.setParentName(changeNameParam);
    }

    @PostMapping("/unbinding")
    public CommonResult<String> unbinding(@RequestBody RelationParam relationParam) {
        return childHomepageService.unbinding(relationParam);
    }

    @PostMapping("/set-anniversary")
    public CommonResult<String> setAnniversary(@RequestBody AnniversaryParam anniversaryParam) {
        return childHomepageService.setAnniversary(anniversaryParam);
    }

    @GetMapping("/all-anniversary")
    public CommonResult<List<AnniversaryResult>> getAnniversaries(Integer userId) {
        return childHomepageService.getAnniversaries(userId);
    }


}
