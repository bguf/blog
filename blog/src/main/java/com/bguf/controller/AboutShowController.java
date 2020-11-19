package com.bguf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 前端，'关于我'跳转
 * @Author
 * @Description
 * @Date 1:17 下午 2020/10/12
 */
@Controller
public class AboutShowController
{
    @GetMapping("/about")
    public String about()
    {
        return "about";
    }
}
