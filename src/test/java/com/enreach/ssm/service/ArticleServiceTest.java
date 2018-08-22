package com.enreach.ssm.service;

import com.enreach.ssm.dto.ArticleDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void add() {

        ArticleDto dto = new ArticleDto();
        dto.setTitle("第10篇");
        dto.setContext("内容信息");
        dto.setTags(new String[]{"jdbc", "spring"});
        dto.setCreator("lizhi");

        int id = articleService.add(dto);
        System.out.println(id);

    }

}
