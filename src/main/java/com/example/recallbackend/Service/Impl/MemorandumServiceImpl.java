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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private MultipartHttpServletRequest multipartHttpServletRequest;

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
    public CommonResult<String> newlyBuildMemorandum(NewlyBuildParam newlyBuildParam) {

        SubmitVideoParam submitVideoParam = new SubmitVideoParam();

        submitVideoParam.setUserId(newlyBuildParam.getUserId());
        submitVideoParam.setData(newlyBuildParam.getData());

        log.info("上传文件");
        boolean b = QiniuUtil.setVideo(scheduleMapper, multipartHttpServletRequest, submitVideoParam.getUserId(),
                submitVideoParam.getData());
        if (!b) {
            return CommonResult.fail("提交失败");
        }

        log.info("设置备忘录");
        int r = timeTableMapper.insertMemorandum(newlyBuildParam);
        if (r == 1) {
            return CommonResult.success("设置成功");
        }
        else {
            return CommonResult.fail("设置失败");
        }

    }
}
