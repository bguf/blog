package com.bguf.dao;

import com.bguf.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签的底层操作
 * @Author
 * @Description
 * @Date 9:49 上午 2020/10/6
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long>
{
    //根据标签的名字，查询标签
    Tag findByName(String name);

    //查询所有的标签
    @Query("select t from Tag t")
    List<Tag> findTags(Pageable pageable);
}
