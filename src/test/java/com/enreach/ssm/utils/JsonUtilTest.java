package com.enreach.ssm.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.enreach.ssm.entity.Article;
import org.junit.Test;

public class JsonUtilTest {

    @Test
    public void json() throws IOException {

        Article article = new Article();
        article.setArticleId(0);
        article.setTitle("title");
        article.setCreateTime(new Date());
        article.setCreator("lizhi");
        article.setIsDelete(false);
        article.setContext("context");

        String json = JsonUtil.serialize(article, true);

        System.out.println(json);

        Article article1 = JsonUtil.deserialize(json, Article.class);

        System.out.println(article1);

        List<Article> list = new ArrayList<>();
        list.add(article);

        json = JsonUtil.serialize(list, false);
        System.out.println(json);


        List<Article> list1 = JsonUtil.deserializeArray(json, Article.class);

        System.out.println(list1.get(0));
        System.out.println(list1.get(0).getCreateTime());

    }
}
