package com.bguf.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author
 * @Description
 * @Date 3:45 下午 2020/10/5
 */
public class LoginInterceptor extends HandlerInterceptorAdapter
{
    /***
     * 拦截后台没有登录，跳转到登录界面
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (request.getSession().getAttribute("manager") == null)
        {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
