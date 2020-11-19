package com.bguf.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.servlet.http.HttpServletRequest;

/**
 * 异常日志，当出现错误时，在控制台可以方便查看日志，跳转到优化界面，提升用户好感度
 * @Author
 * @Description
 * @Date 4:59 下午 2020/10/4
 */
@ControllerAdvice
public class MyExceptionHandler
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception
    {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
        {
            throw e;
        }

        logger.error("Request URL : {}, Exception: {}", request.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView();
        //在erorr界面，通过查看源码，能看到更多的错误信息
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}
