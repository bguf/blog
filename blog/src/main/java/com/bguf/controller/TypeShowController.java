package com.bguf.controller;

import com.bguf.entity.Type;
import com.bguf.service.BlogService;
import com.bguf.service.TypeService;
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
 * 展示类型和类型对应的博客
 * @Author
 * @Description
 * @Date 7:55 上午 2020/10/12
 */
@Controller
public class TypeShowController
{
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    /***
     * 根据类型id展示所有对应的博客
     * @param id
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id,
                        @PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogSearch blog, Model model)
    {
        List<Type> lists = typeService.listTypes();
        if (lists.size() > 0)
        {
            if (id == -1)
            {
                id = lists.get(0).getId();
            }
        }
        BlogSearch blogSearch = new BlogSearch();
        blogSearch.setTypeId(id);
        model.addAttribute("types", lists);
        model.addAttribute("page", blogService.bloglistByType(pageable, blogSearch));
        model.addAttribute("activeTypeid", id);
        return "types";
    }
}
