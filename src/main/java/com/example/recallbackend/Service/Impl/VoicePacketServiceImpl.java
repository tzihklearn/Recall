package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.VoicePacketService;
import com.example.recallbackend.lnterceptor.CurrentUser;
import com.example.recallbackend.lnterceptor.CurrentUserUtil;
import com.example.recallbackend.mapper.ScheduleMapper;
import com.example.recallbackend.mapper.UserRelationMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.result.GetParentResult;
import com.example.recallbackend.pojo.dto.param.SubmitVideoParam;
import com.example.recallbackend.pojo.dto.result.VoiceResult;
import com.example.recallbackend.utils.QiniuUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.23
 */
@Service
@Slf4j
public class VoicePacketServiceImpl implements VoicePacketService {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private UserRelationMapper userRelationMapper;

    @Override
    public CommonResult<List<VoiceResult>> getAllVoice() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();
        Integer userId = currentUser.getId();

        List<VoiceResult> results = scheduleMapper.selectVoiceByUserId(userId);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<List<GetParentResult>> allParent() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();
        List<GetParentResult> results = userRelationMapper.selectParentByChildId(currentUser.getId());

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<VoiceResult> submitVideo(Integer userId, String data, Integer length) {

        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        MultipartHttpServletRequest multipartHttpServletRequest = multipartResolver.resolveMultipart(httpServletRequest);

        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        assert multipartFile != null;
        if (multipartFile == null) {
            return CommonResult.fail("文件不得为空");
        }
        boolean b = QiniuUtil.setVideo(scheduleMapper, multipartFile, userId,
                data, length);

        if (b) {
            VoiceResult voiceResult = scheduleMapper.selectOneByUserId(userId);
            log.info("成功：" + voiceResult.toString());
            return CommonResult.success(voiceResult);
        }
        else {
            return CommonResult.fail("提交失败");
        }

    }

}
