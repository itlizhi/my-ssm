package com.enreach.ssm.dto;

import java.util.Arrays;

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
    public String toString() {
        return "ArticleDto{" +
                "title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", creator='" + creator + '\'' +
                '}';
    }
}
