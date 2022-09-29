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
import com.example.recallbackend.pojo.dto.result.LoginVerificationResult;
import com.example.recallbackend.utils.JwtUtils;
import com.example.recallbackend.utils.RandomUtil;
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
    public CommonResult<LoginVerificationResult> loginVerification(VerificationParam verificationParam) {

//        log.info("验证验证码");
//        Object verificationCode = redisUtil.get(verificationParam.getPhone() + "sms");
//        if (verificationCode == null) {
//            return CommonResult.fail("验证码不存在或已过期");
//        }
//
//        if ( !verificationParam.getVerificationCode().equals(verificationCode)) {
//            return CommonResult.fail("验证码不存在或已过期");
//        }
        LoginVerificationResult result;

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
                result = getLoginVerificationResult(userIds, verificationParam);
                return CommonResult.success(result);
            }
        }
        else {
            userInfo.setUserId(userId);
            userInfo.setState(1);
            int i = userInfoMapper.updateAllByUserId(userInfo);
            if (i == 0) {
                return CommonResult.fail("登陆失败");
            } else {
                result = getLoginVerificationResult(userId, verificationParam);
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
            return CommonResult.fail("登陆失败");
        }
    }

    private String setToken(String userId, String verificationCode) {
        //生成token
        log.info("生成token");
        Map<String, String> payload = new HashMap<>();
        payload.put("userId", userId);
        payload.put("verificationCode", verificationCode);
        return JwtUtils.getToken(payload);
    }

    private LoginVerificationResult getLoginVerificationResult(Integer userIds, VerificationParam verificationParam) {
        String token = setToken(userIds.toString(), verificationParam.getVerificationCode());

        LoginSuccessResult loginByUserId = userInfoMapper.selectLoginByUserId(userIds);

        LoginVerificationResult result = new LoginVerificationResult();

        result.setUserId(loginByUserId.getUserId());
        result.setName(loginByUserId.getName());
        result.setRoleModel(loginByUserId.getRoleModel());
        result.setToken(token);

        return result;
    }

}
