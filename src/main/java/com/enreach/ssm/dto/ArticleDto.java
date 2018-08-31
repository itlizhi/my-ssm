package com.enreach.ssm.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;
import java.util.Objects;

public class ArticleDto {

    private String title;
    private String context;
    private String[] tags;
    private String creator;

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
                '}';
    }
}
