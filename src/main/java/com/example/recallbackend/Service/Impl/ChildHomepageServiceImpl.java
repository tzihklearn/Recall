package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.ChildHomepageService;
import com.example.recallbackend.lnterceptor.CurrentUser;
import com.example.recallbackend.lnterceptor.CurrentUserUtil;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tzih
 * &#064;date  2022.09.21
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
    public CommonResult<UserResult> getName(@RequestParam Integer userId) {
        UserResult result = new UserResult();

        UserInfo userInfo = userInfoMapper.selectAllByUserId(userId);

        result.setUserId(userId);
        result.setName(userInfo.getName());

        return CommonResult.success(result);
    }

    @Override

    public CommonResult<String> changeUserName(@RequestBody NameParam nameParam) {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        int i = userInfoMapper.updateNameByUserId(currentUser.getId(), nameParam.getName());

        if (i == 1) {
            return CommonResult.success("更改用户名成功");
        }
        else {
            return CommonResult.fail("更改用户名失败");
        }
    }

    @Override
    public CommonResult<String> binding(@Valid @RequestBody QRCodeParam qrCodeParam) {

        Integer parentId = userInfoMapper.selectUserIdByQRCode(qrCodeParam.getKey());

        Integer r = userRelationMapper.selectId(qrCodeParam.getUserId(), parentId);
        if ( r != null) {
            return CommonResult.fail("已绑定");
        }

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
    public CommonResult<List<UserResult>> getAllBinding(@RequestParam Integer userId) {

        List<UserResult> results = userRelationMapper.selectParentIdAndNameByChildId(userId);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<String> setParentName(@Valid @RequestBody ChangeNameParam changeNameParam) {

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
    public CommonResult<String> unbinding(@Valid @RequestBody RelationParam relationParam) {
        int i = userRelationMapper.deleteRelation(relationParam.getParentId(), relationParam.getChildId());

        if (i == 1) {
            return CommonResult.success("解绑成功");
        }
        else {
            return CommonResult.fail("解绑失败");
        }
    }

    @Override
    public CommonResult<String> setAnniversary(@Valid @RequestBody AnniversaryParam anniversaryParam) {

        int i = anniversariesMapper.insertAnniversary(anniversaryParam);
        if (i == 0) {
            return CommonResult.fail("提交失败");
        }
        else {
            return CommonResult.success("提交成功");
        }

    }

    @Override
    public CommonResult<List<AnniversaryResult>> getAnniversaries(@RequestParam Integer userId) {

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
            results.add(new AnniversaryResult(anniversaryPo.getAnniversaryId(), anniversaryPo.getData(),
                    TimeUtils.transform(anniversaryPo.getTime()), day));
        }

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<String> removeAnniversary(RemoveParam removeParam) {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        int i = anniversariesMapper.deleteAnniversary(removeParam.getAnniversaryId(), currentUser.getId());

        if (i != 1) {
            return CommonResult.fail("删除失败");
        }

        return CommonResult.success("删除成功");
    }

    @Override
    public CommonResult<String> remove() {

        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();

        int i = anniversariesMapper.deleteByMore(currentUser.getId());

        return CommonResult.success("删除成功");
    }
}
