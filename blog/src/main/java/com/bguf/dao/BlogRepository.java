package com.bguf.dao;

import com.bguf.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 博客的底层操作
 * @Author
 * @Description
 * @Date 12:03 下午 2020/10/6
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog>
{
    //根据 推荐 查询所有已发布的博客
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findBLogs(Pageable pageable);

    //根据博客标题和博客内容模糊查询博客
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1 ")
    Page<Blog> findByQuery(String query, Pageable pageable);

    //根据类型id查询所有博客
    @Query("select b from Blog b where b.type.id = ?1")
    List<Blog> findBlogsByType(Long id);

    //设置浏览博客的数量，Modifying和Transactional注解不可缺，实现类有Transactional，这里就不需要了
    @Modifying
    @Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);

    //根据博客的更新日期（已格式化为如2020这样），查询所有的年份
    @Query("select function('date_format', b.updateTime, '%Y') as year from Blog b group by year order by year desc ")
    List<String> finGroupByYear();

    //根据年份查询所有的博客
    @Query("select b from Blog b where function('date_format', b.updateTime, '%Y') = ?1 and b.published = true")
    List<Blog> findByYear(String year);
}
