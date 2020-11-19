package com.bguf.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author
 * @Description
 * @Date 10:17 下午 2020/10/4
 */
@Entity
@Table(name = "t_comment")
public class Comment
{
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    private String isadmin;//判断评论方的身份，在评论展示时，可以用图像和称呼高亮

    @ManyToOne
    private Blog blog;

    @ManyToOne
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.EAGER)
    private List<Comment> replayComments = new ArrayList<>();

    public Comment()
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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
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

    public Blog getBlog()
    {
        return blog;
    }

    public void setBlog(Blog blog)
    {
        this.blog = blog;
    }

    public Comment getParentComment()
    {
        return parentComment;
    }

    public void setParentComment(Comment parentComment)
    {
        this.parentComment = parentComment;
    }

    public List<Comment> getReplayComments()
    {
        return replayComments;
    }

    public void setReplayComments(List<Comment> replayComments)
    {
        this.replayComments = replayComments;
    }

    public String getIsadmin()
    {
        return isadmin;
    }

    public void setIsadmin(String isadmin)
    {
        this.isadmin = isadmin;
    }
}

