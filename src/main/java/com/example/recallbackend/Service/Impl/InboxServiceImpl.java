package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.InboxService;
import com.example.recallbackend.mapper.TimeTableMapper;
import com.example.recallbackend.mapper.UserInfoMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.domain.UserInfo;
import com.example.recallbackend.pojo.dto.result.InboxDetailsResult;
import com.example.recallbackend.pojo.dto.result.InBoxGetAllResult;
import com.example.recallbackend.pojo.dto.result.temporary.VoiceRecordingResult;
import com.example.recallbackend.pojo.po.IndexGetAllPo;
import com.example.recallbackend.pojo.po.VoiceRecordingPo;
import com.example.recallbackend.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.21
 */
@Service
@Slf4j
public class InboxServiceImpl implements InboxService {

    @Resource
    private TimeTableMapper timeTableMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public CommonResult<List<InBoxGetAllResult>> getAll(Integer userId, Long time) {

        List<InBoxGetAllResult> results = new ArrayList<>();

        int days = 0;
        try {
            days = TimeUtils.TimeSubDay(time, TimeUtils.getDayTime());
        }
        catch (ParseException e) {
            log.warn("时间戳异常");
            e.printStackTrace();
        }

        Long dayTimeEnd = TimeUtils.getDayTimeBefore(days - 1);

        log.info("获取时间安排表");
//        List<TimeSchedulePo> timeSchedulePos = timeTableMapper.selectScheduleByParentId(userId, time, dayTimeEnd);
        List<IndexGetAllPo> indexGetAllPos = timeTableMapper.selectInboxAllByParentId(userId, time, dayTimeEnd);

        for (IndexGetAllPo indexGetAllPo : indexGetAllPos) {
            String times = indexGetAllPo.getTimes();
            List<String> timeList = new ArrayList<>();
            for (String timeString : times.split(",")) {
                timeList.add(TimeUtils.transformHhMm(Long.getLong(timeString, 10)));
            }
            results.add(new InBoxGetAllResult(indexGetAllPo.getUserId(), indexGetAllPo.getName(), indexGetAllPo.getScheduleBoxId(),
                    indexGetAllPo.getData(), timeList));

        }

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<InboxDetailsResult> getDetails(Integer scheduleBoxId, Integer childId, Long time) {

        InboxDetailsResult result = new InboxDetailsResult();

        List<VoiceRecordingResult> voiceRecordingResults = new ArrayList<>();

        int days = 0;
        try {
            days = TimeUtils.TimeSubDay(time, TimeUtils.getDayTime());
        }
        catch (ParseException e) {
            log.warn("时间戳异常");
            e.printStackTrace();
        }

        Long dayTimeEnd = TimeUtils.getDayTimeBefore(days - 1);

        List<VoiceRecordingPo> voiceRecordingPos = timeTableMapper.selectVoiceByUserId(scheduleBoxId, time, dayTimeEnd);

        for (VoiceRecordingPo voiceRecordingPo : voiceRecordingPos) {
            voiceRecordingResults.add(new VoiceRecordingResult(voiceRecordingPo.getData(), voiceRecordingPo.getVideoUrl(),
                    TimeUtils.transformHhMm(voiceRecordingPo.getTimes()), voiceRecordingPo.getState()));
        }

        UserInfo userInfo = userInfoMapper.selectAllByUserId(childId);

        result.setName(userInfo.getName());
        result.setVoiceRecordingList(voiceRecordingResults);

        return CommonResult.success(result);
    }
}
