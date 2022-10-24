package com.example.recallbackend.controller;

import com.example.recallbackend.Service.ChildHomepageService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.*;
import com.example.recallbackend.pojo.dto.result.AnniversaryResult;
import com.example.recallbackend.pojo.dto.result.UserResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tzih
 * &#064;date  2022.09.21
 */
@RestController
@RequestMapping(value = "/child/homepage")
public class ChildHomepageController {

    @Resource
    private ChildHomepageService childHomepageService;

    @GetMapping("/get-name")
    public CommonResult<UserResult> getName(@RequestParam Integer userId) {
        return childHomepageService.getName(userId);
    }

    @PostMapping("/change-user-name")
    public CommonResult<String> changeUserName(@Valid @RequestBody NameParam nameParam) {
        return childHomepageService.changeUserName(nameParam);
    }

    @PostMapping("/binding")
    public CommonResult<String> binding(@Valid @RequestBody QRCodeParam qrCodeParam) {
        return childHomepageService.binding(qrCodeParam);
    }

    @GetMapping("/all")
    public CommonResult<List<UserResult>> getAllBinding(@RequestParam Integer userId) {
        return childHomepageService.getAllBinding(userId);
    }

    @PostMapping("/change-parent-name")
    public CommonResult<String> setParentName(@Valid @RequestBody ChangeNameParam changeNameParam) {
        return childHomepageService.setParentName(changeNameParam);
    }

    @PostMapping("/unbinding")
    public CommonResult<String> unbinding(@Valid @RequestBody RelationParam relationParam) {
        return childHomepageService.unbinding(relationParam);
    }

    @PostMapping("/set-anniversary")
    public CommonResult<String> setAnniversary(@Valid @RequestBody AnniversaryParam anniversaryParam) {
        return childHomepageService.setAnniversary(anniversaryParam);
    }

    @GetMapping("/all-anniversary")
    public CommonResult<List<AnniversaryResult>> getAnniversaries(@RequestParam Integer userId) {
        return childHomepageService.getAnniversaries(userId);
    }

    @DeleteMapping("/del-anniversary")
    public CommonResult<String> removeAnniversary(@RequestBody RemoveParam removeParam) {
        return childHomepageService.removeAnniversary(removeParam);
    }

    @DeleteMapping("/remove")
    public CommonResult<String> remove() {
        return childHomepageService.remove();
    }


}
