package com.bguf.service;

import com.bguf.entity.Blog;
import com.bguf.vo.BlogSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Description
 * @Date 12:01 下午 2020/10/6
 */
public interface BlogService
{
    Blog getBlogById(Long id);
    //前端界面展示，将markdown格式转为html
    Blog getAndConvert(Long id);

    Page<Blog> bloglist(Pageable pageable, BlogSearch blog);
    Page<Blog> bloglist(Pageable pageable);
    //根据tag在前台展示已发布的博客
    Page<Blog> bloglistByTag(Pageable pageable, Long id);
    Page<Blog> bloglist(Pageable pageable, String query);
    //根据type在前台展示已发布的博客
    Page<Blog> bloglistByType(Pageable pageable, BlogSearch blog);

    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);

    List<Blog> listRecommendBlog(Integer size);

    //通过type的id找到blog，删除type时需要
    List<Blog> getBlogByTypeId(Long id);

    Map<String, List<Blog>> archiveBlog();

    Long countblog();
}
