package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.result.GetParentResult;
import com.example.recallbackend.pojo.dto.param.SubmitVideoParam;
import com.example.recallbackend.pojo.dto.result.VoiceResult;

import java.util.List;

public interface VoicePacketService {

    CommonResult<List<VoiceResult>> getAllVoice();

    CommonResult<List<GetParentResult>> allParent();

    CommonResult<VoiceResult> submitVideo(Integer userId, String data, Integer length);

}
