package com.example.recallbackend.config;

import com.example.recallbackend.lnterceptor.MyHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.annotation.Resource;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private MyHandlerInterceptor myHandlerInterceptor;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new BufferedImageHttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置拦截的请求和不拦截的请求
        registry.addInterceptor(myHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**");
    }

}
