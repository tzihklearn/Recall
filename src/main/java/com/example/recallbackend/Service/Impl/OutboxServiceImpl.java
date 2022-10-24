package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.OutboxService;
import com.example.recallbackend.lnterceptor.CurrentUser;
import com.example.recallbackend.lnterceptor.CurrentUserUtil;
import com.example.recallbackend.mapper.*;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.CreatOutBoxParam;
import com.example.recallbackend.pojo.dto.param.temporary.VideoScheduleParam;
import com.example.recallbackend.pojo.dto.result.*;
import com.example.recallbackend.pojo.dto.result.temporary.FeedBackResult;
import com.example.recallbackend.pojo.dto.result.temporary.VoiceRecordingResult;
import com.example.recallbackend.pojo.po.OutBoxGetAllPo;
import com.example.recallbackend.pojo.po.RelationNamePo;
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
public class OutboxServiceImpl implements OutboxService {

    @Resource
    private ScheduleBoxMapper scheduleBoxMapper;

    @Resource
    private UserRelationMapper userRelationMapper;

    @Resource
    private TimeTableMapper timeTableMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public CommonResult<List<OutBoxGetAllResult>> getAllOutBox( String keyWord) {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        List<OutBoxGetAllResult> results = new ArrayList<>();

        List<OutBoxGetAllPo> outBoxGetAllPo = scheduleBoxMapper.selectOutBoxByUserId(currentUser.getId(), keyWord, TimeUtils.getDayTime());

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
    public CommonResult<OutboxDetailsResult> getDetailsOutBox(Integer parentId, Integer childId, Integer scheduleBoxId) {

        OutboxDetailsResult result = new OutboxDetailsResult();

        List<VoiceRecordingResult> voiceRecordingResultList = scheduleBoxMapper.selectVoiceById(scheduleBoxId);

        List<FeedBackResult> feedBackResultList = scheduleBoxMapper.selectFeedBackById(scheduleBoxId);

        RelationNamePo relationNamePo = userRelationMapper.selectNameByUserId(parentId, childId);

        result.setName(relationNamePo.getParentName());

//        //VoiceRecordingResult的time为String,无法转为时间戳
//        List<VoiceRecordingResult> listResult = new ArrayList<>();
//
//        for (VoiceRecordingResult voiceRecordingResult : voiceRecordingResultList) {
//
//        }

        result.setVoiceRecordingList(voiceRecordingResultList);

        result.setFeedBackResultList(feedBackResultList);

        return CommonResult.success(result);
    }

    @Override
    public CommonResult<String> creatOutBox(CreatOutBoxParam creatOutBoxParam) {

        Integer id = userRelationMapper.selectId(creatOutBoxParam.getUserId(), creatOutBoxParam.getParentId());


        log.info("在schedule_box表中插入数据");
        int i = scheduleBoxMapper.insertScheduleByUserId(creatOutBoxParam.getParentId(), creatOutBoxParam.getUserId(),
                creatOutBoxParam.getBoxTime(), id);

        if ( i == 0) {
            log.warn("在schedule_box表中插入数据失败");
            return CommonResult.fail("提交失败");
        }

        int day;
        try {
            day = TimeUtils.TimeSubDay(creatOutBoxParam.getBoxTime(), TimeUtils.getDayTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Long dayTimeBefore = TimeUtils.getDayTimeBefore(day);

        Long dayTimeAfter = TimeUtils.getDayTimeBefore(day - 1);

        Integer scheduleBoxId = scheduleBoxMapper.selectIdByUserIdAndTime(creatOutBoxParam.getParentId(), creatOutBoxParam.getUserId());

        for (VideoScheduleParam videoScheduleParam : creatOutBoxParam.getVideoScheduleParamList()) {
            int j = timeTableMapper.insertBySchedule(creatOutBoxParam.getParentId(), creatOutBoxParam.getUserId(), scheduleBoxId,
                    videoScheduleParam);

            if (j != 1) {
                return CommonResult.fail("提交失败");
            }

        }


        return CommonResult.success("提交成功");


    }

    @Override
    public CommonResult<List<UserResult>> getAllAddressee(Integer userId) {

        List<UserResult> results = userRelationMapper.selectParentIdAndNameByChildId(userId);

        return CommonResult.success(results);

    }

    @Override
    public CommonResult<List<VideoPacketResult>> getAllVideoPacket(Integer userId) {

        List<VideoPacketResult> results = scheduleMapper.selectVideoByUserId(userId);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<List<VideoListResult>> getAllVideo() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();
        Integer userId = currentUser.getId();

        List<VideoListResult> results = scheduleMapper.selectVideosByUserId(userId);

        return CommonResult.success(results);
    }
}
