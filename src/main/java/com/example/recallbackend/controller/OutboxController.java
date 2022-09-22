package com.example.recallbackend.controller;

import com.example.recallbackend.Service.OutboxService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.result.OutBoxGetAllResult;
import com.example.recallbackend.pojo.dto.result.OutboxDetailsResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.21
 */
@RestController
@RequestMapping(value = "/child/outbox", method = RequestMethod.GET)
public class OutboxController {

    @Resource
    private OutboxService outboxService;

    @GetMapping("/all")
    public CommonResult<List<OutBoxGetAllResult>> getAllOutBox(Integer userId) {
        return outboxService.getAllOutBox(userId);
    }

    @GetMapping("/details")
    public CommonResult<OutboxDetailsResult> getDetailsOutBox(Integer parentId, Integer childId) {
        return null;
    }

}
