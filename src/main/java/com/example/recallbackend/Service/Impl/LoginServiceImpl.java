package com.example.recallbackend.Service.Impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.recallbackend.Service.LoginService;
import com.example.recallbackend.common.Constant;
import com.example.recallbackend.mapper.UserInfoMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.domain.UserInfo;
import com.example.recallbackend.pojo.dto.param.PhoneParam;
import com.example.recallbackend.pojo.dto.param.VerificationParam;
import com.example.recallbackend.pojo.dto.result.LoginSuccessResult;
import com.example.recallbackend.utils.JwtUtils.JwtUtils;
import com.example.recallbackend.utils.RandomUtils.RandomUtil;
import com.example.recallbackend.utils.RedisUtils.RedisUtil;
import com.example.recallbackend.utils.smsUtils.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Override
    public CommonResult<String> loginToGetSms(PhoneParam phoneParam) {

        if (!redisUtil.exists(phoneParam.getPhone())) {
            log.info("清楚验证码");
            redisUtil.remove(phoneParam.getPhone());
        }

        log.info("生成验证码");
        String randomNum = RandomUtil.getRandomUtil(4);

        log.info("将验证码配置到redis中");
        redisUtil.set(phoneParam.getPhone() + "sms", randomNum, Constant.expirationTime, TimeUnit.MINUTES);

        String[] randomUtil = {randomNum, Constant.expirationTime.toString()};

        log.info("发送短信验证码");
        SmsUtil.sendSms(phoneParam.getPhone(), randomUtil);

        return CommonResult.success("短信验证码发送成功");
    }

    @Override
    public CommonResult<LoginSuccessResult> loginVerification(VerificationParam verificationParam) {

//        log.info("验证验证码");
//        Object verificationCode = redisUtil.get(verificationParam.getPhone() + "sms");
//        if (verificationCode == null) {
//            return CommonResult.fail("验证码不存在或已过期");
//        }
//
//        if ( !verificationParam.getVerificationCode().equals(verificationCode)) {
//            return CommonResult.fail("验证码不存在或已过期");
//        }
        LoginSuccessResult result;

        //更新数据库
        log.info("更新数据库，注册用户");
        Integer userId = userInfoMapper.selectIdByPhone(verificationParam.getPhone());

        UserInfo userInfo = new UserInfo();

        if (userId == null) {
            userInfo.setName("用户名");
            userInfo.setPhone(verificationParam.getPhone());
            userInfo.setRoleModel(0);
            userInfo.setState(1);
            int i = userInfoMapper.insertAllByUserId(userInfo);

            if (i == 0) {
                return CommonResult.fail("注册失败");
            }
            else {
                Integer userIds = userInfoMapper.selectIdByPhone(verificationParam.getPhone());
                setToken(userIds.toString(), verificationParam.getVerificationCode());

                result = userInfoMapper.selectLoginByUserId(userIds);

                return CommonResult.success(result);
            }
        }
        else {
            userInfo.setUserId(userId);
            userInfo.setState(1);
            int i = userInfoMapper.updateAllByUserId(userInfo);
            if (i == 0) {
                return CommonResult.fail("登陆失败");
            }
            else {
                setToken(userId.toString(), verificationParam.getVerificationCode());

                result = userInfoMapper.selectLoginByUserId(userId);

                return CommonResult.success(result);
            }
        }
    }

    @Override
    public CommonResult<LoginSuccessResult> loginByToken() {

        String token = request.getHeader("Authorization");

        DecodedJWT decode = JwtUtils.decode(token);

        if (decode != null) {

            Integer userId = Integer.valueOf(decode.getClaims().get("userId").toString().replaceAll("\"",""));

            LoginSuccessResult result = userInfoMapper.selectLoginByUserId(userId);

            return CommonResult.success(result);
        }
        else {
            return CommonResult.success("登陆失败");
        }
    }

    private void setToken(String userId, String verificationCode) {
        //生成token
        log.info("生成token");
        Map<String, String> payload = new HashMap<>();
        payload.put("userId", userId);
        payload.put("verificationCode", verificationCode);
        String token = JwtUtils.getToken(payload);

        response.setHeader("Authorization", token);
    }
}
