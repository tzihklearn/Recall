package com.example.recallbackend.Service;

import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.MemorandumParam;

public interface ChildHomepageService {

    CommonResult<String> sendMemorandum(MemorandumParam memorandumParam);

}
