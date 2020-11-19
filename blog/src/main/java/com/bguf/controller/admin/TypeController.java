package com.bguf.controller.admin;

import com.bguf.entity.Blog;
import com.bguf.entity.Type;
import com.bguf.service.BlogService;
import com.bguf.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


/**
 * 类型管理
 * @Author
 * @Description
 * @Date 4:39 下午 2020/10/5
 */
@Controller
@RequestMapping("/admin")
public class TypeController
{
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    /***
     * 类型展示
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String list(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                                   Pageable pageable,
                                    Model model)
    {
        model.addAttribute("page", typeService.typelist(pageable));
        return "admin/types";
    }

    @GetMapping("/types/add")
    public String toAdd(Model model)
    {
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }

    /***
     * @Valid和BindingResult两个必须紧挨在一起，bindingresult在valid后边
     * 更新类型
     * @param type
     * @param bindResult
     * @param id
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String update(@Valid Type type, BindingResult bindResult, @PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        Type t = typeService.getTypeByName(type.getName());
        if (t != null)
        {
            bindResult.rejectValue("name", "nameError",  "此分类已存在");
        }
        if (bindResult.hasErrors())
        {
            return "admin/type-input";
        }
        Type type1 = typeService.updateType(id, type.getName());
        if (type1 == null)
        {
            redirectAttributes.addFlashAttribute("msgf", "更新失败，分类不能为空");
            redirectAttributes.addFlashAttribute("flag", "fail");
        }
        else
        {
            redirectAttributes.addFlashAttribute("msgs", "更新成功");
            redirectAttributes.addFlashAttribute("flag", "success");
        }
        return "redirect:/admin/types";
    }

    /***
     * 添加类型
     * @param type
     * @param bindResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/types")
    public String add(@Valid Type type, BindingResult bindResult, RedirectAttributes redirectAttributes)
    {
        Type t = typeService.getTypeByName(type.getName());
        if (t != null)
        {
            bindResult.rejectValue("name", "nameError",  "此分类已存在");
        }
        if (bindResult.hasErrors())
        {
            return "admin/type-input";
        }
        Type type1 = typeService.saveType(type);
        if (type1 == null)
        {
            redirectAttributes.addFlashAttribute("msgf", "添加失败，分类不能为空");
            redirectAttributes.addFlashAttribute("flag", "fail");
        }
        else
        {
            redirectAttributes.addFlashAttribute("msgs", "添加成功");
            redirectAttributes.addFlashAttribute("flag", "success");
        }
        return "redirect:/admin/types";
    }

    /***
     * 去更新类型
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/edit")
    public String toEdit(@PathVariable Long id, Model model)
    {
        model.addAttribute("type", typeService.getType(id));
        return "admin/type-input";
    }

    /***
     * 删除类型，（类型id和博客id有关联）
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes)
    {
        //根据类型id查询出所有相关博客
        List<Blog> blogs = blogService.getBlogByTypeId(id);
        //和类型有关联的博客，采用以下方式，删除类型
        if (blogs.size() > 0)
        {
            for (Blog blog : blogs)
            {
                //先清空博客中的类型
                blog.setType(null);
                //根据id更新类型
                Type type = typeService.updateType(id, "");
                //再重新设置blog中的类型
                blog.setType(type);
                //最后保存博客
                blogService.saveBlog(blog);
            }
        }
        //与类型没有关联的博客，直接删除类型
        else
        {
            typeService.deleteType(id);
        }
        attributes.addFlashAttribute("flag", "success");
        attributes.addFlashAttribute("msgs", "删除成功");
        return "redirect:/admin/types";
    }
}
