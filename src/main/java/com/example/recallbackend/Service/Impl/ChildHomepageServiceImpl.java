package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.ChildHomepageService;
import com.example.recallbackend.mapper.UserInfoMapper;
import com.example.recallbackend.mapper.UserRelationMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.domain.UserInfo;
import com.example.recallbackend.pojo.dto.param.ChangeNameParam;
import com.example.recallbackend.pojo.dto.param.MemorandumParam;
import com.example.recallbackend.pojo.dto.param.NameParam;
import com.example.recallbackend.pojo.dto.param.QRCodeParam;
import com.example.recallbackend.pojo.dto.result.UserResult;
import com.example.recallbackend.utils.RedisUtils.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tzih
 * @date 2022.09.21
 */
@Service
public class ChildHomepageServiceImpl implements ChildHomepageService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserRelationMapper userRelationMapper;

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
    public CommonResult<String> unbinding(ChangeNameParam changeNameParam) {
        int i = userRelationMapper.deleteRelation(changeNameParam.getChangerId(), changeNameParam.getBeChangerId());

        if (i == 0) {
            return CommonResult.success("解绑成功");
        }
        else {
            return CommonResult.fail("解绑失败");
        }
    }
}
