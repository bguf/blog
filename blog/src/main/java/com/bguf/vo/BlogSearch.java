package com.bguf.vo;

/**
 * @Author
 * @Description
 * @Date 3:55 下午 2020/10/6
 */
public class BlogSearch
{
    private String title;
    private Long typeId;
    private boolean recommend;

    public BlogSearch()
    {
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean isRecommend()
    {
        return recommend;
    }

    public Long getTypeId()
    {
        return typeId;
    }

    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    public void setRecommend(boolean recommend)
    {
        this.recommend = recommend;
    }
}
