package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.MemorandumService;
import com.example.recallbackend.mapper.TimeTableMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.result.MemorandumResult;
import com.example.recallbackend.pojo.po.MemorandumPo;
import com.example.recallbackend.utils.TimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.21
 */
@Service
public class MemorandumServiceImpl implements MemorandumService {

    @Resource
    private TimeTableMapper timeTableMapper;

    @Override
    public CommonResult<List<MemorandumResult>> getAllMemorandum(Integer userId) {

        List<MemorandumResult> results = new ArrayList<>();

        Long nowTime = TimeUtils.getNowTime();

        List<MemorandumPo> memorandumPos = timeTableMapper.selectMemorandumByUserId(userId, nowTime);

        for (MemorandumPo memorandumPo : memorandumPos) {
            Integer lengthInt = memorandumPo.getLength();

            StringBuilder length = new StringBuilder(lengthInt / 60 + ":" + lengthInt%60);

            results.add(new MemorandumResult(memorandumPo.getData(), memorandumPo.getVideoUrl(),
                    TimeUtils.transformNotSeconds(memorandumPo.getTimes()), length.toString()));

        }

        return CommonResult.success(results);
    }
}
