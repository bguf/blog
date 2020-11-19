package com.bguf.config;

import com.bguf.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @Author
 * @Description
 * @Date 3:48 下午 2020/10/5
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport
{
    /**
     * 拦截请求地址时，过滤一些需要访问的url
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/toRegister")
                .excludePathPatterns("/admin/register")
                .excludePathPatterns("/admin/toblogLogin")
                .excludePathPatterns("/admin/bloglogin");
    }

    /***
     * 拦截器会使静态资源不可用，下面的方法可以放行静态资源
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    /*解决No primary or default constructor found for interface org.springframework.data.domain.Pageable分页报错*/
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
    {
        // 注册Spring data jpa pageable的参数分解器
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }
}
