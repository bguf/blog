package com.bguf.service.impl;

import com.bguf.dao.CommentRepository;
import com.bguf.entity.Comment;
import com.bguf.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author
 * @Description
 * @Date 3:29 下午 2020/10/9
 */
@Service
public class ComemntServiceImpl implements CommentService
{
    @Autowired
    CommentRepository commentRepository;

    /***
     * 根据博客id，查询所有的子评论
     * @param blogId
     * @return
     */
    @Override
    @Transactional
    public List<Comment> listCommentsById(Long blogId)
    {
        //按照子评论创建时间升序
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    /***
     * 循环每个顶点的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments)
    {
        List<Comment> commentsView = new ArrayList<>();
        //使用commentsView存放从数据库中查出来的所有父评论
        for (Comment comment : comments)
        {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    //存放迭代找出的所有子代集合
    private static List<Comment> tempReplys = new ArrayList<>();

    /**
     * 合并评论的各层子代到一级子代集合
     * @param comments
     */
    private void combineChildren(List<Comment> comments)
    {
        //循环所有的父评论
        for (Comment comment : comments)
        {
            //得到父评论的所有子评论
            List<Comment> replys1 = comment.getReplayComments();
            for (Comment reply1 : replys1)
            {
                recursively(reply1);
            }
            //设置父评论的所有完整子评论，设置评论层级为两层
            comment.setReplayComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    /***
     * 使用递归，从根节点，到最后的子节点，筛选出所有层级的评论
     * @param comment
     */
    private void recursively(Comment comment)
    {
        //得到根节点，存入列表
        tempReplys.add(comment);
        //如果根节点下还有子节点
        if (comment.getReplayComments().size() > 0)
        {
            //将子节点从列表中取出
            List<Comment> replys = comment.getReplayComments();
            for (Comment reply : replys)
            {
                //继续寻找子节点的子节点
                recursively(reply);
            }
        }
    }

    /***
     * 从页面传来的parentCommentId默认值是-1，以区分父子评论
     * @param comment
     * @return
     */
    @Override
    @Transactional
    public Comment save(Comment comment)
    {
        Long parentCommentId = comment.getParentComment().getId();
        //子评论，需要设置父评论
        if (parentCommentId != -1)
        {
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        }
        else
        {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /***
     * 删除评论
     * @param id
     */
    @Override
    @Transactional
    public void delete(Long id)
    {
        commentRepository.deleteById(id);
    }

    /***
     * 分页查询所有评论
     * @param pageable
     * @return
     */
    @Override
    @Transactional
    public Page<Comment> findComments(Pageable pageable)
    {
        return commentRepository.findComment(pageable);
    }

    /***
     * 根据评论的id查询
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Comment findCommentById(Long id)
    {
        return commentRepository.findById(id).get();
    }

    /***
     * 覆盖不合适的评论
     * @param comment
     * @return
     */
    @Transactional
    public Comment saveFromAdmin(Comment comment)
    {
        return commentRepository.save(comment);
    }
}
