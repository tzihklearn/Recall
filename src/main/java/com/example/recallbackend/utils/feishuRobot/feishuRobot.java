package com.example.recallbackend.utils.feishuRobot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author tzih
 * @date 2022.09.27
 */
@Data
@Component
@ConfigurationProperties(prefix = "feishu-robot")
public class feishuRobot {

    private String hook_url;

    private Boolean enabled;

    private String profile;

//    public void setHook_url(String hook_url) {
//        this.hook_url = hook_url;
//    }


    public static void SendException(Exception exception) {

    }


}
