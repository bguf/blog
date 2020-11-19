package com.bguf.service.impl;

import com.bguf.dao.TagRepository;
import com.bguf.entity.Tag;
import com.bguf.handler.MyNotFindException;
import com.bguf.service.TagService;
import com.bguf.util.StringToLongList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author
 * @Description
 * @Date 9:48 上午 2020/10/6
 */
@Component
public class TagServiceImpl implements TagService
{
    @Autowired
    TagRepository tagRepository;

    /***
     * 保存tag
     * @param tag
     * @return
     */
    @Transactional
    @Override
    public Tag saveTag(Tag tag)
    {
        return tagRepository.save(tag);
    }

    /***
     * 根据标签id查询tag
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Tag getTag(Long id)
    {
        return tagRepository.findById(id).get();
    }

    /***
     * 根据标签名称查询tag
     * @param name
     * @return
     */
    @Transactional
    @Override
    public Tag getTagByName(String name)
    {
        return tagRepository.findByName(name);
    }

    /***
     * 查询所有的标签
     * @return
     */
    @Override
    @Transactional
    public List<Tag> listTags()
    {
        return tagRepository.findAll() ;
    }

    /***
     * 通过blog自定义的tagIds查询标签，并将String类型转为List类型
     * @param tagIds
     * @return
     */
    @Override
    @Transactional
    public List<Tag> listTags(String tagIds)
    {
        return tagRepository.findAllById(StringToLongList.toList(tagIds));
    }

    /***
     * 分页查询所有的标签
     * @param pageable
     * @return
     */
    @Transactional
    @Override
    public Page<Tag> taglist(Pageable pageable)
    {
        return tagRepository.findAll(pageable);
    }

    /***
     * 更新标签
     * @param id
     * @param tag
     * @return
     */
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag)
    {
        Tag tag1 = tagRepository.findById(id).get();
        if (tag1 == null)
        {
            throw new MyNotFindException("找不到此标签");
        }
        BeanUtils.copyProperties(tag, tag1);
        return tagRepository.save(tag1);
    }

    /***
     * 根据标签拥有的博客数量，查询出所有标签，并且降序排序
     * @param size
     * @return
     */
    @Override
    @Transactional
    public List<Tag> listTags(Integer size)
    {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTags(pageable);
    }

    /***
     * 删除标签
     * @param id
     */
    @Transactional
    @Override
    public void deleteTag(Long id)
    {
        tagRepository.deleteById(id);
    }
}
