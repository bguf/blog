package com.bguf.controller.admin;

import com.bguf.entity.Manager;
import com.bguf.service.ManagerService;
import com.bguf.util.FdfsFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 管理员注册
 * @Author
 * @Description
 * @Date 8:57 下午 2020/10/6
 */
@Controller
@RequestMapping("/admin")
public class RegisterController
{
    @Autowired
    private ManagerService managerService;
    @Autowired
    private FdfsFileUpload fdfsFileUpload;

    @GetMapping("/toRegister")
    public String toregister()
    {
        return "admin/register";
    }

    @PostMapping("/register")
    public String register(@Valid Manager manager,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @RequestParam String managername,
                           @RequestParam("image")MultipartFile file)
    {
        manager.setAvatar("-1");
        if (bindingResult.hasErrors())
        {
            return "redirect:/admin/toRegister";
        }
        Manager manager1 = managerService.checkRegister(managername);
        if (manager1 == null)
        {
            try
            {
                String avatar = fdfsFileUpload.upload(file);
                manager.setAvatar(avatar);
                managerService.register(manager);
                redirectAttributes.addFlashAttribute("msgs", "注册成功，请登录");
            }
            catch (Exception e)
            {
                redirectAttributes.addFlashAttribute("msgf", "图片有误");
                return "redirect:/admin/toRegister";
            }
            return "redirect:/admin";
        }
        else
        {
            redirectAttributes.addFlashAttribute("msgf", "已有此用户，注册失败");
            return "redirect:/admin/toRegister";
        }
    }
}
