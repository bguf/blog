package com.bguf.service;

import com.bguf.entity.Tag;
import com.bguf.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author
 * @Description
 * @Date 9:48 上午 2020/10/6
 */
@Service
public interface TagService
{
    //增
    Tag saveTag(Tag tag);
    //查
    Tag getTag(Long id);
    Tag getTagByName(String name);
    //分页
    Page<Tag> taglist(Pageable pageable);
    //改
    Tag updateTag(Long id, Tag tag);
    //删
    void deleteTag(Long id);

    List<Tag> listTags();
    List<Tag> listTags(String tagIds);
    List<Tag> listTags(Integer size);
}
