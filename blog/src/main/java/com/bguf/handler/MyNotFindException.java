package com.bguf.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义异常处理类
 * @Author
 * @Description
 * @Date 5:31 下午 2020/10/4
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyNotFindException extends RuntimeException
{
    public MyNotFindException()
    {
    }

    public MyNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MyNotFindException(String message)
    {
        super(message);
    }
}
