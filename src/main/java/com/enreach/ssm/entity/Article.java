package com.enreach.ssm.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`t_article`")
public class Article implements Serializable {
    @Id
    @Column(name = "`articleId`")
    @GeneratedValue(generator = "JDBC")
    private Integer articleId;

    /**
     * 标题
     */
    @Column(name = "`title`")
    private String title;

    @Column(name = "`createTime`")
    private Date createTime;

    @Column(name = "`creator`")
    private String creator;

    @Column(name = "`isDelete`")
    private Boolean isDelete;

    @Column(name = "`context`")
    private String context;

    private static final long serialVersionUID = 1L;

    /**
     * @return articleId
     */
    public Integer getArticleId() {
        return articleId;
    }

    /**
     * @param articleId
     */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return isDelete
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * @param isDelete
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * @return context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context
     */
    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", articleId=").append(articleId);
        sb.append(", title=").append(title);
        sb.append(", createTime=").append(createTime);
        sb.append(", creator=").append(creator);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", context=").append(context);
        sb.append("]");
        return sb.toString();
    }
}