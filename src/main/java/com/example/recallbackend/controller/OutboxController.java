package com.example.recallbackend.controller;

import com.example.recallbackend.Service.OutboxService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.CreatOutBoxParam;
import com.example.recallbackend.pojo.dto.result.UserResult;
import com.example.recallbackend.pojo.dto.result.OutBoxGetAllResult;
import com.example.recallbackend.pojo.dto.result.OutboxDetailsResult;
import com.example.recallbackend.pojo.dto.result.VideoPacketResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
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
    public CommonResult<List<OutBoxGetAllResult>> getAllOutBox(@NotNull Integer userId, String keyWord) {
        return outboxService.getAllOutBox(userId, keyWord);
    }

    @GetMapping("/details")
    public CommonResult<OutboxDetailsResult> getDetailsOutBox(@NotNull Integer parentId, @NotNull Integer childId,
                                                              @NotNull Integer scheduleBoxId) {
        return outboxService.getDetailsOutBox(parentId, childId, scheduleBoxId);
    }

    @PostMapping("/creat-out-box")
    public CommonResult<String> creatOutBox(@RequestBody CreatOutBoxParam creatOutBoxParam) {
        return outboxService.creatOutBox(creatOutBoxParam);
    }

    @GetMapping("/all-addressee")
    public CommonResult<List<UserResult>> getAllAddressee(@NotNull Integer userId) {
        return outboxService.getAllAddressee(userId);
    }

    @GetMapping("/all-video-packet")
    public CommonResult<List<VideoPacketResult>> getAllVideoPacket(@NotNull Integer userId) {
        return outboxService.getAllVideoPacket(userId);
    }

}
