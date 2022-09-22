package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.OutboxService;
import com.example.recallbackend.mapper.ScheduleBoxMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.result.OutBoxGetAllResult;
import com.example.recallbackend.pojo.dto.result.OutboxDetailsResult;
import com.example.recallbackend.pojo.dto.result.temporary.FeedBackResult;
import com.example.recallbackend.pojo.dto.result.temporary.VoiceRecordingResult;
import com.example.recallbackend.pojo.po.OutBoxGetAllPo;
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
public class OutboxServiceImpl implements OutboxService {

    @Resource
    private ScheduleBoxMapper scheduleBoxMapper;

    @Override
    public CommonResult<List<OutBoxGetAllResult>> getAllOutBox(Integer userId) {

        List<OutBoxGetAllResult> results = new ArrayList<>();

        List<OutBoxGetAllPo> outBoxGetAllPo = scheduleBoxMapper.selectOutBoxByUserId(userId, TimeUtils.getDayTime());

        for (OutBoxGetAllPo boxGetAllPo : outBoxGetAllPo) {
            String times = boxGetAllPo.getTimes();
            List<String> timeList = new ArrayList<>();

            for (String timeString : times.split(",")) {
                timeList.add(TimeUtils.transformHhMm(Long.getLong(timeString, 10)));
            }

            results.add(new OutBoxGetAllResult(boxGetAllPo.getUserId(), boxGetAllPo.getName(), boxGetAllPo.getScheduleBoxId(),
                    boxGetAllPo.getData(), timeList, boxGetAllPo.getUnFeedbackNum()));
        }

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<OutboxDetailsResult> getDetailsOutBox(Integer parentId, Integer childId) {

        OutboxDetailsResult result = new OutboxDetailsResult();

        List<VoiceRecordingResult> voiceRecordingResultList = new ArrayList<>();

        List<FeedBackResult> feedBackResultList = new ArrayList<>();






        return null;
    }
}
