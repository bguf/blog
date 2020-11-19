package com.bguf.controller;

import com.bguf.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 前端归档处理
 * @Author
 * @Description
 * @Date 7:55 上午 2020/10/12
 */
@Controller
public class ArchiveShowController
{
    @Autowired
    private BlogService blogService;

    /***
     * 将所有年份和有每一个年份有关联的博客放入map中，key为年份，value为所有博客集合
     * @param model
     * @return
     */
    @GetMapping("/archives")
    public String archive(Model model)
    {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogcount", blogService.countblog());
        return "archives";
    }
}
