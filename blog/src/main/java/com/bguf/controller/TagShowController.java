package com.bguf.controller;

import com.bguf.entity.Tag;
import com.bguf.service.BlogService;
import com.bguf.service.TagService;
import com.bguf.vo.BlogSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 展示标签和标签对应的博客
 * @Author
 * @Description
 * @Date 7:55 上午 2020/10/12
 */
@Controller
public class TagShowController
{
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    /***
     * 根据标签id展示对应的博客
     * @param id
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}")
    public String types(@PathVariable Long id,
                        @PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogSearch blog, Model model)
    {
        List<Tag> lists = tagService.listTags();
        if (lists.size() > 0)
        {
            //刚开始默认展示第一个标签
            if (id == -1)
            {
                id = lists.get(0).getId();
            }
        }
        model.addAttribute("tags", lists);
        model.addAttribute("page", blogService.bloglistByTag(pageable, id));
        model.addAttribute("activeTagid", id);
        return "tags";
    }
}
