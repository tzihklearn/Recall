package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.InboxService;
import com.example.recallbackend.mapper.ScheduleMapper;
import com.example.recallbackend.mapper.TimeTableMapper;
import com.example.recallbackend.mapper.UserRelationMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.SubmitVideoParam;
import com.example.recallbackend.pojo.dto.result.InboxDetailsResult;
import com.example.recallbackend.pojo.dto.result.InBoxGetAllResult;
import com.example.recallbackend.pojo.dto.result.temporary.VoiceRecordingResult;
import com.example.recallbackend.pojo.po.IndexGetAllPo;
import com.example.recallbackend.pojo.po.RelationNamePo;
import com.example.recallbackend.pojo.po.VideoPo;
import com.example.recallbackend.pojo.po.VoiceRecordingPo;
import com.example.recallbackend.utils.QiniuUtil;
import com.example.recallbackend.utils.TimeUtils;
import com.sun.media.sound.WaveFileReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private MultipartHttpServletRequest multipartHttpServletRequest;

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
            voiceRecordingResults.add(new VoiceRecordingResult(voiceRecordingPo.getData(), voiceRecordingPo.getVideoUrl(),
                    TimeUtils.transformHhMm(voiceRecordingPo.getTimes()), voiceRecordingPo.getState()));
        }

        RelationNamePo relationNamePo = userRelationMapper.selectNameByUserId(parentId, childId);
        result.setName(relationNamePo.getChildName());
        result.setVoiceRecordingList(voiceRecordingResults);

        return CommonResult.success(result);
    }

    @Override
    public CommonResult<String> feedback(SubmitVideoParam submitVideoParam) {

        MultipartFile multipartFile = multipartHttpServletRequest.getFile("video");

        try {
            log.info("将文件上传");
            assert multipartFile != null;
            InputStream inputStream = multipartFile.getInputStream();
            log.info("以当前时间戳为文件名");
            String key = submitVideoParam.getUserId() + TimeUtils.getNowTime() + ".wav";
            String filesName = QiniuUtil.UploadFiles(inputStream, key);

            if (filesName == null) {
                log.warn("未获得文件名，文件上传失败");
                return CommonResult.fail("文件上传失败");
            }

            log.info("获取音频播放链接");
            String videoUrl = QiniuUtil.DownloadFiles(filesName);

            log.info("获取音频文件帧率");
            WaveFileReader waveFileReader = new WaveFileReader();

            //
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            AudioFileFormat audioFileFormat = waveFileReader.getAudioFileFormat(bufferedInputStream);

            int length = audioFileFormat.getFrameLength();

            VideoPo videoPo = new VideoPo();

            videoPo.setUserId(submitVideoParam.getUserId());
            videoPo.setData(submitVideoParam.getData());
            videoPo.setVideoUrl(videoUrl);
            videoPo.setLength(length);

            int i = scheduleMapper.insertVideoByUserId(videoPo);

            if (i == 0) {
                return CommonResult.fail("提交失败");
            }
            else {
                return CommonResult.success("提交成功");
            }

        } catch (IOException e) {
            log.warn("数据流获取异常");
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }

    }
}
