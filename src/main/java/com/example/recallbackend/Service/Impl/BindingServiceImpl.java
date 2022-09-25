package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.BindingService;
import com.example.recallbackend.mapper.UserInfoMapper;
import com.example.recallbackend.mapper.UserRelationMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.domain.UserInfo;
import com.example.recallbackend.pojo.dto.param.ChangeNameParam;
import com.example.recallbackend.pojo.dto.param.NameParam;
import com.example.recallbackend.pojo.dto.param.RelationParam;
import com.example.recallbackend.pojo.dto.param.UserIdParam;
import com.example.recallbackend.pojo.dto.result.UserResult;
import com.example.recallbackend.utils.Md5Util;
import com.example.recallbackend.utils.MyQRCodeUtil;
import com.example.recallbackend.utils.RedisUtils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author tzih
 * @date 2022.09.19
 */
@Service
@Slf4j
public class BindingServiceImpl implements BindingService {

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
    public CommonResult<List<UserResult>> getAllBinding(Integer parentId) {

        List<UserResult> results = userRelationMapper.selectChildByParentId(parentId);

        return CommonResult.success(results);
    }

    @Override
    public CommonResult<String> parentChangeName(NameParam nameParam) {

//        Integer userId = Integer.valueOf(request.getHeader("jwt"));

        int i = userInfoMapper.updateNameByUserId(nameParam.getUserId(), nameParam.getName());

        if (i == 1) {
            return CommonResult.success("更改用户名成功");
        }
        else {
            return CommonResult.fail("更改用户名失败");
        }

    }

    @Override
    public CommonResult<String> setChildName(ChangeNameParam changeNameParam) {

//        Integer userId = Integer.valueOf(request.getHeader("jwt"));

        if (changeNameParam.getName() == null) {
            return CommonResult.fail("姓名不得为空");
        }

        int i = userRelationMapper.updateChildName(changeNameParam.getChangerId(), changeNameParam.getBeChangerId(), changeNameParam.getName());
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
    public CommonResult<String> creatQRCode(UserIdParam userIdParam) {

        UserInfo userInfo = userInfoMapper.selectAllByUserId(userIdParam.getUserId());

        log.info("生成md5加密信息");
        String md5Str = Md5Util.getMD5Str(userInfo.getUserId().toString() + userInfo.getName());

        log.info("将md5加密信息存入数据库");
        int i = userInfoMapper.updateQRCodeByUserIdInt(userIdParam.getUserId(), md5Str);
        if (i == 0) {
            log.warn("md5加密数据存入数据库失败");
            return CommonResult.fail("生成二维码失败");
        }

        log.info("生成二维码base64字节数组");
        String qrCode = MyQRCodeUtil.getQRCode(md5Str);

        boolean set = redisUtil.set(userIdParam.getUserId().toString() + "QRCode", md5Str, 30, TimeUnit.MINUTES);

        if (set) {
            return CommonResult.success(qrCode);
        }
        else {
            return CommonResult.fail("生成二维码失败");
        }

    }
}
