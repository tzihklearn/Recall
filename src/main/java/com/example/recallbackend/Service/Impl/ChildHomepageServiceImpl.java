package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.ChildHomepageService;
import com.example.recallbackend.mapper.AnniversariesMapper;
import com.example.recallbackend.mapper.UserInfoMapper;
import com.example.recallbackend.mapper.UserRelationMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.domain.UserInfo;
import com.example.recallbackend.pojo.dto.param.*;
import com.example.recallbackend.pojo.dto.result.AnniversaryResult;
import com.example.recallbackend.pojo.dto.result.UserResult;
import com.example.recallbackend.pojo.po.AnniversaryPo;
import com.example.recallbackend.utils.RedisUtils.RedisUtil;
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
public class ChildHomepageServiceImpl implements ChildHomepageService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserRelationMapper userRelationMapper;

    @Resource
    private AnniversariesMapper anniversariesMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public CommonResult<UserResult> getName(Integer userId) {
        UserResult result = new UserResult();

        UserInfo userInfo = userInfoMapper.selectAllByUserId(userId);

        result.setUserId(userId);
        result.setName(userInfo.getName());

        return CommonResult.success(result);
    }

    @Override

    public CommonResult<String> changeUserName(NameParam nameParam) {
        int i = userInfoMapper.updateNameByUserId(nameParam.getUserId(), nameParam.getName());

        if (i == 1) {
            return CommonResult.success("更改用户名成功");
        }
        else {
            return CommonResult.fail("更改用户名失败");
        }
    }

    @Override
    public CommonResult<String> binding(QRCodeParam qrCodeParam) {

        Integer parentId = userInfoMapper.selectUserIdByQRCode(qrCodeParam.getKey());

        if (parentId == null) {
            return CommonResult.fail("二维码不存在");
        }

        String qrCode = (String)redisUtil.get(parentId + "QRCode");

        if ( !qrCode.equals(qrCodeParam.getKey()) ) {
            return CommonResult.fail("二维码不存在或已过期");
        }

        int i = userRelationMapper.insertRelationByUserId(parentId, qrCodeParam.getUserId());

        if (i == 0) {
            return CommonResult.fail("绑定失败");
        }
        else {
            return CommonResult.success("绑定成功");
        }

    }

    @Override
    public CommonResult<List<UserResult>> getAllBinding(Integer userId) {

        List<UserResult> results = userRelationMapper.selectParentByChildId(userId);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<String> setParentName(ChangeNameParam changeNameParam) {

        if (changeNameParam.getName() == null) {
            return CommonResult.fail("姓名不得为空");
        }

        int i = userRelationMapper.updateParentName(changeNameParam.getBeChangerId(), changeNameParam.getChangerId(),
                changeNameParam.getName());
        if (i == 1) {
            return CommonResult.success("更新成功");
        }
        else {
            return CommonResult.fail("更新失败");
        }
    }

    @Override
    public CommonResult<String> unbinding(RelationParam relationParam) {
        int i = userRelationMapper.deleteRelation(relationParam.getParentId(), relationParam.getChildId());

        if (i == 0) {
            return CommonResult.success("解绑成功");
        }
        else {
            return CommonResult.fail("解绑失败");
        }
    }

    @Override
    public CommonResult<String> setAnniversary(AnniversaryParam anniversaryParam) {

        int i = anniversariesMapper.insertAnniversary(anniversaryParam);
        if (i == 0) {
            return CommonResult.fail("提交失败");
        }
        else {
            return CommonResult.success("提交成功");
        }

    }

    @Override
    public CommonResult<List<AnniversaryResult>> getAnniversaries(Integer userId) {

        List<AnniversaryResult> results = new ArrayList<>();

        long dayTime = TimeUtils.getNowTime();

        List<AnniversaryPo> anniversaryPoList = anniversariesMapper.selectAnniversaryPoByUserId(userId, dayTime);

        for (AnniversaryPo anniversaryPo : anniversaryPoList) {
            int day;
            try {
                day = TimeUtils.TimeSubDay(dayTime, anniversaryPo.getTime());
            } catch (ParseException e) {
                log.info("时间戳转换异常");
                throw new RuntimeException(e);
            }
            results.add(new AnniversaryResult(anniversaryPo.getData(), TimeUtils.transform(anniversaryPo.getTime()), day));
        }

        return CommonResult.success(results);
    }
}
