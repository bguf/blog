package com.bguf.controller.admin;

import com.bguf.entity.Tag;
import com.bguf.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 标签管理
 * @Author
 * @Description
 * @Date 9:50 上午 2020/10/6
 */
@Controller
@RequestMapping("/admin")
public class TagController
{
    @Autowired
    TagService tagService;

    /***
     * 标签展示
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String list(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                       Pageable pageable,
                       Model model)
    {
        model.addAttribute("page", tagService.taglist(pageable));
        return "admin/tags";
    }

    /***
     * 去添加标签
     * @param model
     * @return
     */
    @GetMapping("/tags/add")
    public String toAdd(Model model)
    {
        model.addAttribute("tag", new Tag());
        return "admin/tag-input";
    }

    /***
     * 编辑更新标签
     * @param tag
     * @param bindingResult
     * @param id
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String update(@Valid Tag tag,
                      BindingResult bindingResult,
                      @PathVariable Long id,
                      RedirectAttributes redirectAttributes)
    {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null)
        {
            bindingResult.rejectValue("name", "nameError", "此标签已存在");
        }
        if (bindingResult.hasErrors())
        {
            return "admin/tag-input";
        }
        Tag tag2 = tagService.updateTag(id, tag);
        if (tag2 == null)
        {
            redirectAttributes.addFlashAttribute("msgf", "更新失败，标签不能为空");
            redirectAttributes.addFlashAttribute("flag", "fail");
        }
        else
        {
            redirectAttributes.addFlashAttribute("msgs", "更新成功");
            redirectAttributes.addFlashAttribute("flag", "success");
        }
        return "redirect:/admin/tags";
    }

    /***
     * 添加标签
     * @param tag
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/tags")
    public String add(@Valid Tag tag,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes)
    {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (bindingResult.hasErrors())
        {
            return "admin/tag-input";
        }
        Tag tag2 = tagService.saveTag(tag);
        if (tag2 == null)
        {
            redirectAttributes.addFlashAttribute("msgf", "添加失败，标签不能为空");
            redirectAttributes.addFlashAttribute("flag", "fail");
        }
        else
        {
            redirectAttributes.addFlashAttribute("msgs", "添加成功");
            redirectAttributes.addFlashAttribute("flag", "success");
        }
        return "redirect:/admin/tags";
    }

    /***
     * 去编辑标签，准备更新
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/edit")
    public String toEdit(@PathVariable Long id, Model model)
    {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tag-input";
    }

    /***
     * 删除标签
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("flag", "success");
        redirectAttributes.addFlashAttribute("msgs", "删除成功");
        return "redirect:/admin/tags";
    }
}
