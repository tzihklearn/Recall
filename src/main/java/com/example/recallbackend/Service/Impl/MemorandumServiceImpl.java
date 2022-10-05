package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.MemorandumService;
import com.example.recallbackend.mapper.ScheduleMapper;
import com.example.recallbackend.mapper.TimeTableMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.dto.param.NewlyBuildParam;
import com.example.recallbackend.pojo.dto.param.SubmitVideoParam;
import com.example.recallbackend.pojo.dto.result.MemorandumResult;
import com.example.recallbackend.pojo.po.MemorandumPo;
import com.example.recallbackend.utils.QiniuUtil;
import com.example.recallbackend.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tzih
 * @date 2022.09.21
 */
@Service
@Slf4j
public class MemorandumServiceImpl implements MemorandumService {

    @Resource
    private TimeTableMapper timeTableMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

//    @Autowired
////    @Resource
//    private MultipartHttpServletRequest multipartHttpServletRequest;
    @Resource
    private HttpServletRequest httpServletRequest;

    @Override
    public CommonResult<List<MemorandumResult>> getAllMemorandum(Integer userId) {

        List<MemorandumResult> results = new ArrayList<>();

        Long nowTime = TimeUtils.getNowTime();

        List<MemorandumPo> memorandumPos = timeTableMapper.selectMemorandumByUserId(userId, nowTime);

        for (MemorandumPo memorandumPo : memorandumPos) {
            long lengthInt = memorandumPo.getLength().longValue();

            String length = TimeUtils.transformMmSs(lengthInt);

            results.add(new MemorandumResult(memorandumPo.getData(), memorandumPo.getVideoUrl(),
                    TimeUtils.transformNotSeconds(memorandumPo.getTimes()), length));

        }

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<String> newlyBuildMemorandum(Integer userId, String data, Long time, Integer length) {

        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        MultipartHttpServletRequest multipartHttpServletRequest = multipartResolver.resolveMultipart(httpServletRequest);

        SubmitVideoParam submitVideoParam = new SubmitVideoParam();

        NewlyBuildParam newlyBuildParam = new NewlyBuildParam(userId, data, time);

        submitVideoParam.setUserId(newlyBuildParam.getUserId());
        submitVideoParam.setData(newlyBuildParam.getData());
//        System.out.println(submitVideoParam);
        log.info("上传文件");

//        Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
//        System.out.println(fileMap);
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        assert multipartFile != null;
        boolean b = QiniuUtil.setVideo(scheduleMapper, multipartFile, submitVideoParam.getUserId(),
                submitVideoParam.getData(), length);
        if (!b) {
            return CommonResult.fail("提交失败");
        }
        Integer scheduleId = scheduleMapper.selectIdByOrder(userId);
        log.info("设置备忘录");
        int r = timeTableMapper.insertMemorandum(newlyBuildParam, scheduleId);
        if (r == 1) {
            return CommonResult.success("设置成功");
        }
        else {
            return CommonResult.fail("设置失败");
        }

    }
}