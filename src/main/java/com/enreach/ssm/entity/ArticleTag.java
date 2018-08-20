package com.enreach.ssm.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`t_article_tag`")
public class ArticleTag implements Serializable {
    @Id
    @Column(name = "`articleId`")
    private Integer articleId;

    @Id
    @Column(name = "`tagId`")
    private Integer tagId;

    @Column(name = "`createTime`")
    private Date createTime;

    @Column(name = "`creator`")
    private String creator;

    @Column(name = "`isDelete`")
    private Boolean isDelete;

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
     * @return tagId
     */
    public Integer getTagId() {
        return tagId;
    }

    /**
     * @param tagId
     */
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
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
        this.creator = creator == null ? null : creator.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", articleId=").append(articleId);
        sb.append(", tagId=").append(tagId);
        sb.append(", createTime=").append(createTime);
        sb.append(", creator=").append(creator);
        sb.append(", isDelete=").append(isDelete);
        sb.append("]");
        return sb.toString();
    }
}