package com.example.recallbackend.utils.feishuRobot;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author tzih
 * @date 2022.09.27
 */
@Aspect
@Component
public class feishuRpbotAop {

    @Pointcut("execution( * com.example.recallbackend.controller.*.*(..))")
    public void pointcut() {

    }

    @AfterThrowing(value = "pointcut()", throwing = "exception")
    public void ExceptionRobot(Exception exception) {

    }

}
