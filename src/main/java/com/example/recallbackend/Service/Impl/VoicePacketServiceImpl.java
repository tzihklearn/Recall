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

        MultipartFile multipartFile = multipartHttpServletRequest.getFile("video");

        try {
            log.info("将文件上传");
            assert multipartFile != null;
            InputStream inputStream = multipartFile.getInputStream();
            log.info("以当前时间戳为文件名");
            String key = TimeUtils.getNowTime() + ".wav";
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
