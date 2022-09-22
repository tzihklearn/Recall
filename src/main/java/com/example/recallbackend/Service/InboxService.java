package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.result.InboxDetailsResult;
import com.example.recallbackend.pojo.dto.result.InBoxGetAllResult;

import java.util.List;

public interface InboxService {

    CommonResult<List<InBoxGetAllResult>> getAll(Integer userId, Long time);

    CommonResult<InboxDetailsResult> getDetails(Integer scheduleBoxId, Integer childId, Long time);

}
