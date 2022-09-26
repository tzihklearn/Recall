package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.NewlyBuildParam;
import com.example.recallbackend.pojo.dto.result.MemorandumResult;

import java.util.List;

public interface MemorandumService {

    CommonResult<List<MemorandumResult>> getAllMemorandum(Integer userId);

    CommonResult<String> newlyBuildMemorandum(NewlyBuildParam newlyBuildParam);

}
