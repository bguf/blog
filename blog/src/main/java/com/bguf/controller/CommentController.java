package com.bguf.controller;

import com.bguf.entity.Comment;
import com.bguf.entity.Manager;
import com.bguf.entity.User;
import com.bguf.service.BlogService;
import com.bguf.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 前端评论管理
 * @Author
 * @Description
 * @Date 3:22 下午 2020/10/9
 */
@Controller
public class CommentController
{
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    private String avatar;

    /***
     * 前台通过博客id获取所有评论
     * @param blogId
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String commentlist(@PathVariable Long blogId, Model model, HttpSession session)
    {
        List<Comment> list = commentService.listCommentsById(blogId);
        if (list.size() == 0)
        {
            session.setAttribute("count", "0");
        }
        else
        {
            session.setAttribute("count", "1");
            model.addAttribute("comments", list);
        }
        return "blogs :: commentlist";
    }

    /***
     * 前台提交评论
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session)
    {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlogById(blogId));
        Object objUser  = session.getAttribute("user");
        Object objManager = session.getAttribute("manager");

        if (objUser != null)
        {
            User user = (User)objUser;
            comment.setAvatar(user.getAvatar());
            comment.setIsadmin("0");
        }
        else if (objManager != null)
        {
            Manager manager = (Manager)objManager;
            comment.setAvatar(manager.getAvatar());
            comment.setIsadmin("1");
        }
        else
        {
            comment.setAvatar(avatar);
        }
        commentService.save(comment);
        return "redirect:/comments/" + blogId;
    }

    /***
     * 后台评论列表
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/admin/comments")
    public String list(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable,
                       Model model)
    {
        model.addAttribute("page", commentService.findComments(pageable));
        return "admin/comments";
    }

    /***
     * 后台删除评论，根据评论id，（子评论和上一级的评论有关联）
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/admin/comments/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes)
    {
        Comment parentComment = commentService.findCommentById(id);
        if (parentComment.getReplayComments().size() == 0)
        {
            commentService.delete(id);
        }
        else
        {
            for (Comment reply : parentComment.getReplayComments())
            {
                //先将上一级的评论设为空值
                reply.setParentComment(null);
                //再更新上一级的评论内容
                parentComment.setContent("此评论内容违规，不可见");
                //保存上一级的评论
                commentService.saveFromAdmin(parentComment);
                //设置子评论
                reply.setParentComment(parentComment);
                //保存子评论
                commentService.saveFromAdmin(reply);
            }
        }
        attributes.addFlashAttribute("flag", "success");
        attributes.addFlashAttribute("msgs", "删除成功");
        return "redirect:/admin/comments";
    }
}
