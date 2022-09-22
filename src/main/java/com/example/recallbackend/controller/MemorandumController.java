package com.example.recallbackend.controller;

import com.example.recallbackend.Service.MemorandumService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.result.MemorandumResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.21
 */
@RestController
@RequestMapping(value = "/parent/memorandum", method = RequestMethod.GET)
public class MemorandumController {

    @Resource
    private MemorandumService memorandumService;

    @GetMapping("/all")
    public CommonResult<List<MemorandumResult>> getAllMemorandum(@NotNull Integer userId) {
        return memorandumService.getAllMemorandum(userId);
    }

}
