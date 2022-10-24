package com.example.recallbackend.controller;

import com.example.recallbackend.Service.MemorandumService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.NewlyBuildParam;
import com.example.recallbackend.pojo.dto.result.MemorandumResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.21
 */
@RestController
@RequestMapping(value = "/parent/memorandum")
public class MemorandumController {

    @Resource
    private MemorandumService memorandumService;

    @GetMapping("/all")
    public CommonResult<List<MemorandumResult>> getAllMemorandum(@RequestParam Integer userId) {
        return memorandumService.getAllMemorandum(userId);
    }

    @PostMapping("/newly-build")
    public CommonResult<String> newlyBuildMemorandum(@RequestParam Integer userId, @RequestParam String data,
                                                     @RequestParam Long time, @RequestParam Integer length) {
        return memorandumService.newlyBuildMemorandum(userId, data, time, length);
    }


}
