package com.example.recallbackend.controller;

import com.example.recallbackend.Service.VoicePacketService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.result.GetParentResult;
import com.example.recallbackend.pojo.dto.result.VoiceResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.23
 */
@RestController
@RequestMapping(value = "/child/voice-packet")
public class VoicePacketController {

    @Resource
    private VoicePacketService voicePacketService;

    @GetMapping("/all")
    public CommonResult<List<VoiceResult>> getAllVoice() {
        return voicePacketService.getAllVoice();
    }

    @GetMapping("/all-parent")
    public CommonResult<List<GetParentResult>> allParent() {
        return voicePacketService.allParent();
    }

    @PostMapping("/submit")
    public CommonResult<VoiceResult> submitVideo(Integer userId, String data, Integer length) {
        return voicePacketService.submitVideo(userId, data, length);
    }

}
