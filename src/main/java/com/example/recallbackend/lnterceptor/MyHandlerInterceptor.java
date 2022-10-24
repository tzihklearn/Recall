package com.example.recallbackend.lnterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.recallbackend.mapper.UserInfoMapper;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.pojo.domain.UserInfo;
import com.example.recallbackend.utils.JwtUtils;
import com.example.recallbackend.utils.RedisUtils.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tzih
 * @date 2022.09.13
 */
@Component
@Slf4j
public class MyHandlerInterceptor implements HandlerInterceptor {

//    @Resource
//    private HttpServletRequest myRequest;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = request.getHeader("Authorization");

        if (token == null) {
            log.info("没有token");
            return false;
        }

        DecodedJWT decode = JwtUtils.decode(token);
//        String userId = request.getParameter("userId");
//&& !decode.getClaims().get("userId").toString().equals(userId)
        if (decode != null ) {

            log.info("token校验通过");

            String toString = decode.getClaim("userId").toString();
            String userId = toString.replaceAll("\"", "");

            Object userId1 = redisUtil.get(userId);
            if (userId1!=null) {
                log.info("数据库校验");
                UserInfo userInfo = userInfoMapper.selectById(Integer.valueOf(userId));

                if (userInfo != null) {

                    CurrentUser currentUser = new CurrentUser();
                    currentUser.setId(userInfo.getUserId());

                    CurrentUserUtil.setCurrentUser(currentUser);

                    return true;
                }
            }

        }
        else {
            log.warn("token校验未通过");

            setResponse(response);

            return false;
        }

        setResponse(response);
        return false;
//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    public void setResponse(HttpServletResponse response) {
        CommonResult<Object> objectCommonResult = CommonResult.token_error();

        try {
            //将 map装换为json ResponseBody底层使用jackson
            String json = new ObjectMapper().writeValueAsString(objectCommonResult);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
