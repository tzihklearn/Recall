package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.result.OutBoxGetAllResult;
import com.example.recallbackend.pojo.dto.result.OutboxDetailsResult;

import java.util.List;

public interface OutboxService {

    CommonResult<List<OutBoxGetAllResult>> getAllOutBox(Integer userId);

    CommonResult<OutboxDetailsResult> getDetailsOutBox(Integer parentId, Integer childId, Integer scheduleBoxId);
}
