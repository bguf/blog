package com.bguf.controller;

import com.bguf.entity.Blog;
import com.bguf.entity.User;
import com.bguf.service.BlogService;
import com.bguf.service.TagService;
import com.bguf.service.TypeService;
import com.bguf.service.UserService;
import com.bguf.util.FdfsFileUpload;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 前台展示博客，含有其他一部分功能
 * @Author
 * @Description
 * @Date 5:04 下午 2020/10/8
 */
@Controller
public class IndexController
{
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;
    @Autowired
    private FdfsFileUpload fdfsFileUpload;

    /***
     * 用户想要评论，必须注册登录，传入blogid，方便在用户登录后，直接回到应该评论的博客评论区
     * @param blogid
     * @param session
     * @return
     */
    @GetMapping("/tologin/{blogid}")
    public String loginPage(@PathVariable Long blogid, HttpSession session)
    {
        if (blogid != -1)
        {
            session.setAttribute("blogid", blogid);
        }
        return "login";
    }

    /***
     * 前台展示已发布的博客
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model)
    {
        model.addAttribute("page", blogService.bloglist(pageable));
        model.addAttribute("types", typeService.listTypes(6));
        model.addAttribute("tags", tagService.listTags(10));
        model.addAttribute("recommend", blogService.listRecommendBlog(8));
        return "index";
    }

    /***
     * 用户登录
     * @param nickname
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String nickname,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes)
    {
        long blogid = Long.parseLong(String.valueOf(session.getAttribute("blogid")));
        User user = userService.checkUser(nickname, password);
        if (user != null)
        {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "redirect:/blog/"+blogid+"#comment";
        }
        else
        {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/tologin/"+blogid;
        }
    }

    /***
     * 用户去注册
     * @return
     */
    @GetMapping("/toRegister")
    public String toRegister()
    {
        return "register";
    }

    /***
     * 用户注册
     * @param user
     * @param bindingResult
     * @param redirectAttributes
     * @param nickname
     * @param session
     * @return
     */
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @RequestParam String nickname,
                           HttpSession session,
                           @RequestParam("image")MultipartFile file)
    {
        user.setAvatar("-1");
        if (bindingResult.hasErrors())
        {
            return "redirect:/toRegister";
        }
        User user1 = userService.checkRegister(nickname);
        if (user1 == null)
        {

            long blogid = 0;
            try
            {
                String avatar = fdfsFileUpload.upload(file);
                user.setAvatar(avatar);
                userService.register(user);
                redirectAttributes.addFlashAttribute("msgs", "注册成功，请登录");
                blogid = Long.parseLong(String.valueOf(session.getAttribute("blogid")));
            }
            catch (Exception e)
            {
                redirectAttributes.addFlashAttribute("msgf", "图片有误");
                return "redirect:/toRegister";
            }
            return "redirect:/tologin/"+blogid;
        }
        else
        {
            redirectAttributes.addFlashAttribute("msgf", "已有此用户，注册失败");
            return "redirect:/toRegister";
        }
    }

    /***
     * 博客从数据库拿出后，不能直接展示，需要转换成html文本
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model)
    {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blogs";
    }

    /***
     * 前端博客查询
     * @param pageable
     * @param model
     * @param query
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                                     Pageable pageable,
                         Model model, @RequestParam String query)
    {
        model.addAttribute("page", blogService.bloglist(pageable, "%"+query+"%"));
        model.addAttribute("query", query);
        return "search";
    }

    /***
     * 用户注销
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logOut(HttpSession session)
    {
        if (session.getAttribute("user") != null)
        {
            session.removeAttribute("user");
        }
        else if (session.getAttribute("manager") != null)
        {
            session.removeAttribute("manager");
        }
        return "redirect:/tologin/-1";
    }

    /***
     * 每一个页面下的footer都有最新博客，将最新的3条博客放到该区域
     * @param model
     * @param blog
     * @return
     */
    @GetMapping("/footer/newblog")
    public String newBlogs(Model model, Blog blog)
    {
        model.addAttribute("newBlogs", blogService.listRecommendBlog(3));
        return "_fragments :: newblogslist";
    }
}
