package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.SubmitVideoParam;

public interface VoicePacketService {

    CommonResult<String> submitVideo(SubmitVideoParam submitVideoParam);

}
