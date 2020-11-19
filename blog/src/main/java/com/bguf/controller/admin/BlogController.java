package com.bguf.controller.admin;

import com.bguf.entity.Blog;
import com.bguf.entity.Manager;
import com.bguf.service.BlogService;
import com.bguf.service.TagService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 管理员博客管理
 * @Author
 * @Description
 * @Date 3:42 下午 2020/10/5
 */
@Controller
@RequestMapping("/admin")
public class BlogController
{
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /***
     * 获取数据库中所有的博客信息，返回给展示界面
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String list(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogSearch blog, Model model)
    {
        model.addAttribute("page", blogService.bloglist(pageable, blog));
        model.addAttribute("types", typeService.listTypes());
        return "admin/blogs";
    }

    /***
     * 根据条件组合，查找符合条件的博客信息，返回给展示块
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogSearch blog, Model model)
    {
        model.addAttribute("page", blogService.bloglist(pageable, blog));
        return "admin/blogs :: bloglist";
    }

    /***
     * 前往博客编辑
     * @param model
     * @return
     */
    @GetMapping("/blogs/toadd")
    public String toAdd(Model model)
    {
        model.addAttribute("blog", new Blog());
        initialTypeAndTag(model);
        return "admin/blogs-input";
    }

    /***
     * 提取出来的公共方法，放入已找到的类型列表和标签列表
     * @param model
     */
    private void initialTypeAndTag(Model model)
    {
        model.addAttribute("types", typeService.listTypes());
        model.addAttribute("tags", tagService.listTags());
    }

    /***
     * 根据博客id，到博客编辑界面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/toEdit/{id}")
    public String toEdit(@PathVariable Long id, Model model)
    {
        Blog blog =  blogService.getBlogById(id);
        blog.init();
        model.addAttribute("blog", blog);
        initialTypeAndTag(model);
        return "admin/blogs-input";
    }

    /***
     * 博客编辑好后，写入到数据库
     * @param blog
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/blogs/addedit")
    public String addEdit(Blog blog, HttpSession session, RedirectAttributes redirectAttributes)
    {
        blog.setManager((Manager) session.getAttribute("manager"));
        //不使用blog.setType(typeService.getType(blog.getType().getId()))，因为在getType().getId()时，getType()空指针;
        blog.setType(typeService.getType(blog.getTypeId()));
        blog.setTags(tagService.listTags(blog.getTagIds()));
        //提取博客内容的一部分，作为摘要展示
        String content = blog.getContent();
        int len;
        if (content.length() > 30)
        {
            len = 20;
        }
        else
        {
            len = content.length()/2;
        }
        blog.setDescriptor(String.copyValueOf(content.toCharArray(), 0, len) + "...");
        //判断是否添加或更新
        Blog blog1 = null;
        if (blog.getId() == null)
        {
            blog1 = blogService.saveBlog(blog);
        }
        else
        {
            blog1 = blogService.updateBlog(blog.getId(), blog);
        }
        if (blog1 == null)
        {
            redirectAttributes.addFlashAttribute("msgf", "操作失败");
            redirectAttributes.addFlashAttribute("flag", "fail");
        }
        else
        {
            redirectAttributes.addFlashAttribute("msgs", "操作成功");
            redirectAttributes.addFlashAttribute("flag", "success");
        }
        return "redirect:/admin/blogs";
    }

    /***
     * 根据id删除博客
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/blogs/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("msgs", "删除成功");
        redirectAttributes.addFlashAttribute("flag", "success");
        return "redirect:/admin/blogs";
    }
}
