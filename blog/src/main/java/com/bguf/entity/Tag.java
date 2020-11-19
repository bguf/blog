package com.bguf.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @Description
 * @Date 10:16 下午 2020/10/4
 */
@Entity
@Table(name = "t_tag")
public class Tag
{
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "标签分类不能为空")
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private List<Blog> blogs = new ArrayList<>();

    public Tag()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Blog> getBlogs()
    {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs)
    {
        this.blogs = blogs;
    }
}
