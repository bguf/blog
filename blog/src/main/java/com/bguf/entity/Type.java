package com.bguf.entity;



import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @Description
 * @Date 10:14 下午 2020/10/4
 */
@Entity
@Table(name = "t_type")
public class Type
{
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    private List<Blog> blogList = new ArrayList<>();

    public Type()
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

    public List<Blog> getBlogList()
    {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList)
    {
        this.blogList = blogList;
    }
}
