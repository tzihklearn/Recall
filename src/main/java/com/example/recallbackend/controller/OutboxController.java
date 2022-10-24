package com.example.recallbackend.controller;

import com.example.recallbackend.Service.OutboxService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.CreatOutBoxParam;
import com.example.recallbackend.pojo.dto.result.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.21
 */
@RestController
@RequestMapping(value = "/child/outbox")
public class OutboxController {

    @Resource
    private OutboxService outboxService;

    @GetMapping("/all")
    public CommonResult<List<OutBoxGetAllResult>> getAllOutBox(@RequestParam(required = false) String keyWord) {
        return outboxService.getAllOutBox(keyWord);
    }

    @GetMapping("/details")
    public CommonResult<OutboxDetailsResult> getDetailsOutBox(@RequestParam Integer parentId, @RequestParam Integer childId,
                                                              @RequestParam Integer scheduleBoxId) {
        return outboxService.getDetailsOutBox(parentId, childId, scheduleBoxId);
    }

    @PostMapping("/creat-out-box")
    public CommonResult<String> creatOutBox(@Valid @RequestBody CreatOutBoxParam creatOutBoxParam) {
        return outboxService.creatOutBox(creatOutBoxParam);
    }

    @GetMapping("/all-addressee")
    public CommonResult<List<UserResult>> getAllAddressee(@RequestParam Integer userId) {
        return outboxService.getAllAddressee(userId);
    }

    @GetMapping("/all-video-packet")
    public CommonResult<List<VideoPacketResult>> getAllVideoPacket(@RequestParam Integer userId) {
        return outboxService.getAllVideoPacket(userId);
    }

    @GetMapping("/all-video")
    public CommonResult<List<VideoListResult>> getAllVideo() {
        return outboxService.getAllVideo();
    }

}