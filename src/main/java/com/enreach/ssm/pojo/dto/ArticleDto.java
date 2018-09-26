package com.enreach.ssm.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;

public class ArticleDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String context;

    /**
     * 使用properties文件名必须是 ValidationMessages.properties
     */
    @NotNull(message = "{ssm.validator.tags}")
    private String[] tags;
    private String creator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ArticleDto)) return false;

        ArticleDto dto = (ArticleDto) o;

        return new EqualsBuilder()
                .append(title, dto.title)
                .append(context, dto.context)
                .append(tags, dto.tags)
                .append(creator, dto.creator)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(context)
                .append(tags)
                .append(creator)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
