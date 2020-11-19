package com.bguf.service;

import com.bguf.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author
 * @Description
 * @Date 3:28 下午 2020/10/9
 */
public interface CommentService
{
    List<Comment> listCommentsById(Long blogId);

    Comment save(Comment comment);

    void delete(Long id);

    Page<Comment> findComments(Pageable pageable);

    Comment findCommentById(Long id);

    Comment saveFromAdmin(Comment comment);
}
