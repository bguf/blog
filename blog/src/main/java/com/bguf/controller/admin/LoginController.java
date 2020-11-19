package com.bguf.controller.admin;

import com.bguf.entity.Manager;
import com.bguf.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 管理员登录
 * @Author
 * @Description
 * @Date 1:58 下午 2020/10/5
 */
@Controller
@RequestMapping("/admin")
public class LoginController
{
    @Autowired
    private ManagerService managerService;

    /***
     * 管理员前往登录
     * @param session
     * @return
     */
    @GetMapping
    public String loginPage(HttpSession session)
    {
        //用户登录界面也会有管理员登录的选项，方便评论博客；传入back，代表后台登录，登录成功后，直接进入后台
        session.setAttribute("backorfront", "back");
        return "admin/login";
    }

    /***
     * 管理员登录信息提交后，根据匹配成功与否，选择跳转不同界面
     * @param managername
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String managername,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes)
    {
        Manager manager = managerService.checkManager(managername, password);
        if (manager != null)
        {
            manager.setPassword(null);
            session.setAttribute("manager", manager);
            return "admin/index";
        }
        else
        {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

    /***
     * 处理不规范的操作，（通过网址进入管理界面）
     * @return
     */
    @GetMapping("/login")
    public String loginGet(HttpSession session)
    {
        if (session.getAttribute("manager") != null)
        {
            return "admin/index";
        }
        else
        {
            return "redirect:/admin";
        }
    }

    /***
     * 管理员注销
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logOut(HttpSession session)
    {
        session.removeAttribute("manager");
        return "redirect:/admin";
    }

    /***
     * 前台管理员登录
     * @param session
     * @return
     */
    @GetMapping("/toblogLogin")
    public String toblogLogin(HttpSession session)
    {
        session.setAttribute("backorfront", "front");
        return "admin/login";
    }

    /***
     * 登录成功后，直接进入之前的博客评论界面
     * @param managername
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/bloglogin")
    public String bloglogin(@RequestParam String managername,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes)
    {
        Manager manager = managerService.checkManager(managername, password);
        long blogid = Long.parseLong(String.valueOf(session.getAttribute("blogid")));
        if (manager != null)
        {
            manager.setPassword(null);
            session.setAttribute("manager", manager);
            session.setAttribute("count", "1");
            return "redirect:/blog/"+blogid+"#comment";
        }
        else
        {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin/toblogLogin";
        }
    }
}
