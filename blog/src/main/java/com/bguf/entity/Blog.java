package com.bguf.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客
 * @Author
 * @Description
 * @Date 10:06 下午 2020/10/4
 */
@Entity
@Table(name = "t_blog")
public class Blog
{
    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;

    private String firstPicture;
    private String flag;//博客标记，（原创，转载，翻译）
    private Integer views;//浏览次数
    private boolean appreciation;//是否开启赞赏
    private boolean shareStatement;//是否允许被转载
    private boolean commentable;//是否允许被评论
    private boolean published;//是否发布
    private boolean recommend;//是否推荐
    private String descriptor;//摘要
    @Transient
    private String tagIds;//标签id，不存入数据库，在处理数据时用
    @Transient
    private Long typeId;//类型id，功能同上一个
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @ManyToOne
    private Type type;
    @ManyToMany(fetch = FetchType.EAGER)//懒加载失败时使用
    private List<Tag> tags = new ArrayList<>();
    @ManyToOne
    private Manager manager;
    @OneToMany(mappedBy = "blog")
    private List<Comment> commentList = new ArrayList<>();

    public Blog()
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getFirstPicture()
    {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture)
    {
        this.firstPicture = firstPicture;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    public Integer getViews()
    {
        return views;
    }

    public void setViews(Integer views)
    {
        this.views = views;
    }

    public boolean isAppreciation()
    {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation)
    {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement()
    {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement)
    {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentable()
    {
        return commentable;
    }

    public void setCommentable(boolean commentable)
    {
        this.commentable = commentable;
    }

    public boolean isPublished()
    {
        return published;
    }

    public void setPublished(boolean published)
    {
        this.published = published;
    }

    public boolean isRecommend()
    {
        return recommend;
    }

    public void setRecommend(boolean recommend)
    {
        this.recommend = recommend;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public List<Tag> getTags()
    {
        return tags;
    }

    public void setTags(List<Tag> tags)
    {
        this.tags = tags;
    }

    public Manager getManager()
    {
        return manager;
    }

    public void setManager(Manager manager)
    {
        this.manager = manager;
    }

    public List<Comment> getCommentList()
    {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList)
    {
        this.commentList = commentList;
    }

    public String getTagIds()
    {
        return tagIds;
    }

    public void setTagIds(String tagIds)
    {
        this.tagIds = tagIds;
    }

    public Long getTypeId()
    {
        return typeId;
    }

    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    public String getDescriptor()
    {
        return descriptor;
    }

    public void setDescriptor(String descriptor)
    {
        this.descriptor = descriptor;
    }

    public void init()
    {
        this.tagIds = tagsToIds(this.getTags());//tags是个列表，blog展示需要的是字符串，在blogs-input页面,展示用
        this.typeId = this.getType().getId();//编辑blog时，需要用到
    }

    private String tagsToIds(List<Tag> tags)
    {
        if (!tags.isEmpty())
        {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags)
            {
                if (flag)
                {
                    ids.append(",");
                }
                else
                {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }
        else
        {
            return tagIds;
        }
    }
}
