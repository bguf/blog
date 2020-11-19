package com.bguf.service.impl;

import com.bguf.dao.BlogRepository;
import com.bguf.entity.Blog;
import com.bguf.entity.Type;
import com.bguf.handler.MyNotFindException;
import com.bguf.service.BlogService;
import com.bguf.util.MarkdownUtils;
import com.bguf.util.MyBeanUtils;
import com.bguf.vo.BlogSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * 博客接口实现类
 * @Author
 * @Description
 * @Date 12:03 下午 2020/10/6
 */
@Service
public class BlogServiceImpl implements BlogService
{
    @Autowired
    BlogRepository blogRepository;


    /***
     * 根据博客类型的id，得到博客
     * @param id
     * @return
     */
    @Override
    @Transactional
    public List<Blog> getBlogByTypeId(Long id)
    {
        return blogRepository.findBlogsByType(id);
    }

    /***
     * 根据博客id，得到博客
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Blog getBlogById(Long id)
    {
        return blogRepository.findById(id).get();
    }


    /***
     * 根据3个条件组合，查询符合条件的博客
     * @param pageable
     * @param blog
     * @return
     */
    @Override
    @Transactional
    public Page<Blog> bloglist(Pageable pageable, BlogSearch blog)
    {
        return blogRepository.findAll(new Specification<Blog>()
        {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder builder)
            {
                List<Predicate> predicates = new ArrayList<>();
                //标题模糊匹配
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null)
                {
                    predicates.add(builder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                //blog不能直接得到type对象，所以如果使用blog.getType().getId()会报空指针异常
                if (blog.getTypeId() != null)
                {
                    predicates.add(builder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend())
                {
                    predicates.add(builder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    @Transactional
    public Page<Blog> bloglistByType(Pageable pageable, BlogSearch blog)
    {
        return blogRepository.findAll(new Specification<Blog>()
        {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder builder)
            {
                List<Predicate> predicates = new ArrayList<>();
                //blog不能直接得到type对象，所以如果使用blog.getType().getId()会报空指针异常
                if (blog.getTypeId() != null)
                {
                    predicates.add(builder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                predicates.add(builder.equal(root.<Boolean>get("published"), true));
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    /***
     * 通过blog中的tags联表查询博客
     * @param pageable
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Page<Blog> bloglistByTag(Pageable pageable, Long id)
    {
        return blogRepository.findAll(new Specification<Blog>()
        {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cb)
            {
                List<Predicate> predicates = new ArrayList<>();
                Join join = root.join("tags");
                predicates.add(cb.equal(join.get("id"), id));
                predicates.add(cb.equal(root.<Boolean>get("published"), true));
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    /***
     * 查询出已发布的博客
     * @param pageable
     * @return
     */
    @Override
    @Transactional
    public Page<Blog> bloglist(Pageable pageable)
    {
        return blogRepository.findAll(new Specification<Blog>()
        {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder builder)
            {
                Predicate predicate = builder.equal(root.<Boolean>get("published"), true);
                query.where(predicate);
                return null;
            }
        }, pageable);
    }

    /**
     * 根据查询条件查询出博客列表
     * @param pageable
     * @param query
     * @return
     */
    @Override
    @Transactional
    public Page<Blog> bloglist(Pageable pageable, String query)
    {
        return blogRepository.findByQuery(query, pageable);
    }

    /***
     * 保存博客
     * @param blog
     * @return
     */
    @Override
    @Transactional
    public Blog saveBlog(Blog blog)
    {
        //博客添加
        if (blog.getId() == null)
        {
            //初始化博客创建、更新日期、浏览次数和标记
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
            if (blog.getFlag() == null || "".equals(blog.getFlag()))
            {
                blog.setFlag("原创");
            }
        }
        else
        {
            //博客更新
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    /***
     * 更新博客
     * @param id
     * @param blog
     * @return
     */
    @Override
    @Transactional
    public Blog updateBlog(Long id, Blog blog)
    {
        Blog blog1 = blogRepository.findById(id).get();
        if (blog1 == null)
        {
            throw new MyNotFindException("该博客不存在");
        }
        //只将blog中有值的赋给blog1
        BeanUtils.copyProperties(blog, blog1, MyBeanUtils.getNullPropertyNames(blog));
        return saveBlog(blog1);
    }

    /***
     * 根据年份归档
     * @return
     */
    @Override
    @Transactional
    public Map<String, List<Blog>> archiveBlog()
    {
        //得到所有博客的年份
        List<String> years = blogRepository.finGroupByYear();
        //根据年份，查找所有年份对应的博客，并存入map，LinkedHashMap存的时候是有序的，取的时候也是有序的
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : years)
        {
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    /***
     * 博客的总数
     * @return
     */
    @Override
    @Transactional
    public Long countblog()
    {
        return blogRepository.count();
    }

    /***
     * 将数据库中博客的markdown类型的内容转为html文本
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Blog getAndConvert(Long id)
    {
        Blog blog = blogRepository.findById(id).get();
        if (blog == null)
        {
            throw new MyNotFindException("该博客不存在");
        }
        Blog blog1 = new Blog();
        //拷贝博客，不操作原数据库数据
        BeanUtils.copyProperties(blog, blog1);
        String content = blog1.getContent();
        //转html
        blog1.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //数据库存入浏览更新次数
        blogRepository.updateViews(id);
        //blog1取的是更新浏览次数前的数据，所以在下边，额外设置blog1的浏览次数，以达到更新的效果
        blog1.setViews(blog1.getViews() + 1);
        return blog1;
    }

    /***
     * 根据更新时间，展示最新的size条博客
     * @param size
     * @return
     */
    @Override
    @Transactional
    public List<Blog> listRecommendBlog(Integer size)
    {
        //先将所有博客排序
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        //再调用下边的bloglist方法，查出已发布的博客
        Page<Blog> page = bloglist(pageable);
        return page.toList();
    }

    /***
     * 根据博客id删除博客
     * @param id
     */
    @Override
    @Transactional
    public void deleteBlog(Long id)
    {
        blogRepository.deleteById(id);
    }
}
