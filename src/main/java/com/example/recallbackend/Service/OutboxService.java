package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.CreatOutBoxParam;
import com.example.recallbackend.pojo.dto.result.*;

import java.util.List;

public interface OutboxService {

    CommonResult<List<OutBoxGetAllResult>> getAllOutBox( String keyWord);

    CommonResult<OutboxDetailsResult> getDetailsOutBox(Integer parentId, Integer childId, Integer scheduleBoxId);

    CommonResult<String> creatOutBox(CreatOutBoxParam creatOutBoxParam);

    CommonResult<List<UserResult>> getAllAddressee(Integer userId);

    CommonResult<List<VideoPacketResult>> getAllVideoPacket(Integer userId);

    CommonResult<List<VideoListResult>> getAllVideo();
}
