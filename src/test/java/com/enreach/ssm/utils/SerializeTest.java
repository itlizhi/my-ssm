package com.enreach.ssm.utils;

import java.util.Date;

import com.enreach.ssm.entity.Article;
import org.junit.Test;

public class SerializeTest {

    @Test
    public void serialize() {

        Article article = new Article();
        article.setArticleId(1);
        article.setTitle("title");
        article.setCreateTime(new Date());
        article.setCreator("lizhi");
        article.setIsDelete(false);
        article.setContext("context");

        byte[] bytes = SerializerUtil.serialize(article);

        Article article1 = SerializerUtil.deserialize(bytes, Article.class);

        System.out.println(article1);


        String val = "hello world";

        bytes = SerializerUtil.serialize(val);

        String str = SerializerUtil.deserialize(bytes, String.class);
        System.out.println(str);

    }


}
