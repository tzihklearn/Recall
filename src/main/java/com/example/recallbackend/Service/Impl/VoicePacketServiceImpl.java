package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.VoicePacketService;
import com.example.recallbackend.mapper.ScheduleMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.SubmitVideoParam;
import com.example.recallbackend.pojo.po.VideoPo;
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

/**
 * @author tzih
 * @date 2022.09.23
 */
@Service
@Slf4j
public class VoicePacketServiceImpl implements VoicePacketService {

    @Resource
    private MultipartHttpServletRequest multipartHttpServletRequest;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public CommonResult<String> submitVideo(SubmitVideoParam submitVideoParam) {

        boolean b = QiniuUtil.setVideo(scheduleMapper, multipartHttpServletRequest, submitVideoParam.getUserId(),
                submitVideoParam.getData());

        if (b) {
            return CommonResult.success("提交成功");
        }
        else {
            return CommonResult.fail("提交失败");
        }

    }

}
