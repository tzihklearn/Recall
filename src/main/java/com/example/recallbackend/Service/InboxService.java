package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.SubmitVideoParam;
import com.example.recallbackend.pojo.dto.result.InboxDetailsResult;
import com.example.recallbackend.pojo.dto.result.InBoxGetAllResult;

import java.util.List;

public interface InboxService {

    CommonResult<List<InBoxGetAllResult>> getAll(Integer userId, Long time);

    CommonResult<InboxDetailsResult> getDetails(Integer scheduleBoxId, Integer parentId, Integer childId, Long time);

    CommonResult<String> feedback(SubmitVideoParam submitVideoParam);
}
