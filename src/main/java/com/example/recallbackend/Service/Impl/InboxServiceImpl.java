package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.InboxService;
import com.example.recallbackend.mapper.ScheduleBoxMapper;
import com.example.recallbackend.mapper.ScheduleMapper;
import com.example.recallbackend.mapper.TimeTableMapper;
import com.example.recallbackend.mapper.UserRelationMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.ConfirmParam;
import com.example.recallbackend.pojo.dto.result.InboxDetailsResult;
import com.example.recallbackend.pojo.dto.result.InBoxGetAllResult;
import com.example.recallbackend.pojo.dto.result.temporary.VoiceRecordingResult;
import com.example.recallbackend.pojo.po.IndexGetAllPo;
import com.example.recallbackend.pojo.po.RelationNamePo;
import com.example.recallbackend.pojo.po.VoiceRecordingPo;
import com.example.recallbackend.utils.QiniuUtil;
import com.example.recallbackend.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    private UserRelationMapper userRelationMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private ScheduleBoxMapper scheduleBoxMapper;

    @Resource
    private HttpServletRequest httpServletRequest;
//    private MultipartHttpServletRequest multipartHttpServletRequest;

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

                timeList.add(TimeUtils.transformHhMm(Integer.parseInt(timeString)));
            }
            results.add(new InBoxGetAllResult(indexGetAllPo.getUserId(), indexGetAllPo.getName(), indexGetAllPo.getScheduleBoxId(),
                    indexGetAllPo.getData(), timeList));

        }

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<InboxDetailsResult> getDetails(Integer scheduleBoxId, Integer parentId, Integer childId, Long time) {

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
            voiceRecordingResults.add(new VoiceRecordingResult(voiceRecordingPo.getVoiceId(), voiceRecordingPo.getData(),
                    voiceRecordingPo.getVideoUrl(), TimeUtils.transformHhMm(voiceRecordingPo.getTimes()), voiceRecordingPo.getTimes(),
                    voiceRecordingPo.getState()));
        }

        RelationNamePo relationNamePo = userRelationMapper.selectNameByUserId(parentId, childId);
        result.setName(relationNamePo.getChildName());
        result.setVoiceRecordingList(voiceRecordingResults);

        return CommonResult.success(result);
    }

    @Override
    public CommonResult<String> feedback(Integer parentId, Integer childId, Integer scheduleBoxId, Integer length) {

//        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        MultipartHttpServletRequest multipartHttpServletRequest = multipartResolver.resolveMultipart(httpServletRequest);
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");


//        String path = "./";
//        OutputStream os = null;
//        byte[] bytes;
//        int len;
//        try {
//            InputStream inputStream = multipartFile.getInputStream();
//            bytes = multipartFile.getBytes();
//
//            File file = new File(path);
//
//            os = new FileOutputStream(file.getPath() + File.separator + "test");
//
//
//            while ((len = inputStream.read(bytes)) != -1) {
//                os.write(bytes, 0, len);
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        assert multipartFile != null;
        boolean b = QiniuUtil.setVideo(scheduleMapper, multipartFile, parentId, null, length);

        if (!b) {
            return CommonResult.fail("提交失败");
        }

        Integer scheduleId = scheduleMapper.selectIdByOrder(parentId);

        int i = timeTableMapper.insertFeedbackBy(parentId, childId, scheduleId, scheduleBoxId);

        if (i == 0) {
            return CommonResult.fail("提交失败");
        }

        int j = scheduleBoxMapper.updateUnFeedbackById(scheduleBoxId);
        if (j == 0) {
            return CommonResult.fail("提交失败");
        }

        return CommonResult.success("提交成功");
    }

    @Override
    public CommonResult<String> confirm(ConfirmParam confirmParam) {

        int i = timeTableMapper.updateState(confirmParam);
        if (i == 0) {
            return CommonResult.fail("更新失败");
        }

        return CommonResult.success("更新成功");
    }
}
