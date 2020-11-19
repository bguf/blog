package com.bguf.dao;

import com.bguf.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 评论的底层操作
 * @Author
 * @Description
 * @Date 3:31 下午 2020/10/9
 */
public interface CommentRepository extends JpaRepository<Comment, Long>
{
    //根据博客id、排序规则和父评论为空的评论（即父评论），查询所有的父评论
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

    //查询所有的评论
    @Query("select comment from Comment comment")
    Page<Comment> findComment(Pageable pageable);
}
