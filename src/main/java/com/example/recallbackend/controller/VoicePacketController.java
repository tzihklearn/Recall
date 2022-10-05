package com.example.recallbackend.controller;

import com.example.recallbackend.Service.VoicePacketService;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.SubmitVideoParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tzih
 * @date 2022.09.23
 */
@RestController
@RequestMapping(value = "/child/voice-packet")
public class VoicePacketController {

    @Resource
    private VoicePacketService voicePacketService;

//    @GetMapping("/all")
//    public CommonResult

    @PostMapping("/submit")
    public CommonResult<String> submitVideo(@RequestBody SubmitVideoParam submitVideoParam) {
        return voicePacketService.submitVideo(submitVideoParam);
    }

}
