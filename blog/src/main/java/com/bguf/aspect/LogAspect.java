package com.bguf.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 利用aop切面编程，增强日志功能
 * @Author
 * @Description
 * @Date 6:25 下午 2020/10/4
 */
@Aspect
@Component
public class LogAspect
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /***
     * controller下的所有方法都作为切入点被匹配
     */
    @Pointcut("execution(* com.bguf.controller.*.*(..))")
    public void log()
    {

    }

    /***
     * 在方法执行前执行，获取访问服务器和ip，方法所在类，方法名和方法参数
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint)
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String name = joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, name, args);
        logger.info("request:{}", requestLog);
    }

    @After("log()")
    public void doAfter()
    {
//        System.out.println("-------after--------");
    }

    /***
     * 方法执行后，返回方法返回结果
     * @param result
     */
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result)
    {
        logger.info("result:{}", result);
    }

    private class RequestLog
    {
        private String url;
        private String ip;
        private String methodName;
        private Object[] args;

        public RequestLog(String url, String ip, String methodName, Object[] args)
        {
            this.url = url;
            this.ip = ip;
            this.methodName = methodName;
            this.args = args;
        }

        @Override
        public String toString()
        {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", methodName='" + methodName + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
