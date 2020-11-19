package com.bguf.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author
 * @Description
 * @Date 10:22 下午 2020/10/4
 */
@Entity
@Component
@Table(name = "t_manager")
public class Manager
{
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotBlank(message = "用户名不能为空")
    private String managername;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "邮箱不能为空")
    private String email;
    private String avatar;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @OneToMany(mappedBy = "manager")
    private List<Blog> blogList = new ArrayList<>();

    public Manager()
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

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getManagername()
    {
        return managername;
    }

    public void setManagername(String managername)
    {
        this.managername = managername;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
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
