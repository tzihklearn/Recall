package com.example.recallbackend.Service.Impl;

import com.example.recallbackend.Service.LoginService;
import com.example.recallbackend.common.Constant;
import com.example.recallbackend.mapper.UserInfoMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.domain.UserInfo;
import com.example.recallbackend.pojo.dto.param.PhoneParam;
import com.example.recallbackend.pojo.dto.param.VerificationParam;
import com.example.recallbackend.utils.JwtUtils.JwtUtils;
import com.example.recallbackend.utils.RandomUtils.RandomUtil;
import com.example.recallbackend.utils.RedisUtils.RedisUtil;
import com.example.recallbackend.utils.smsUtils.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public CommonResult<String> loginVerification(VerificationParam verificationParam) {

        log.info("验证验证码");
        Object verificationCode = redisUtil.get(verificationParam.getPhone() + "sms");
        if (verificationCode == null) {
            return CommonResult.fail("验证码不存在或已过期");
        }

        if ( !verificationParam.getVerificationCode().equals(verificationCode)) {
            return CommonResult.fail("验证码不存在或已过期");
        }

        log.info("生成token");
        //生成token
        Map<String, String> payload = new HashMap<>();
        payload.put("phone", verificationParam.getPhone());
        payload.put("verificationCode", verificationParam.getVerificationCode());
        String token = JwtUtils.getToken(payload);

        response.setHeader("Authorization", token);

        //更新数据库
        log.info("更新数据库，注册用户");
        Integer userId = userInfoMapper.selectIdByPhone(verificationParam.getPhone());

        UserInfo userInfo = new UserInfo();

        if (userId == null) {
            userInfo.setPhone(verificationParam.getPhone());
            userInfo.setState(1);
            int i = userInfoMapper.insertAllByUserId(userInfo);
            if (i == 0) {
                return CommonResult.fail("注册失败");
            }
            else {
                return CommonResult.success("登陆成功");
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
                return CommonResult.success("登陆成功");
            }
        }
    }

    @Override
    public CommonResult<String> loginByToken() {

        String token = response.getHeader("Authorization");

        boolean decode = JwtUtils.decode(token);

        if (decode) {
            return CommonResult.success("登陆成功");
        }
        else {
            return CommonResult.success("登陆失败");
        }
    }
}
