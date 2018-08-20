package com.enreach.ssm.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.enreach.ssm.entity.Article;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.mybatis.mapper.entity.Example;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class ArticleDaoTest {

    @Autowired
    private ArticleMapper articleMapper;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testInsert() {
        //LOG.debug("=======================");

        Article article = new Article();
        article.setTitle("no12");
        article.setCreateTime(new Date());
        article.setCreator("lizhi");
        article.setContext("Mybatis-generator(MBG)是Mybatis官网发布的一个用来生成代码的一个工具包，他可以对一些简单的CRUD应用进行快速的生成POJO对象和对应的Mapper接口文件及XML配置文件，大大的减少了手写的误差和工作量");

        articleMapper.insertSelective(article);

        System.out.println(article.getArticleId());

    }

    @Test
    public void testExample() {

        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("articleId", 22);
        articleMapper.selectByExample(example);

        Article article = new Article();
        article.setTitle("no5");
        articleMapper.select(article);

    }

    @Test
    public void testExtend() {
       List<Article> list= articleMapper.selectByWhere("articleId>30 and title like '%n%' ");

        System.out.println(list);

        boolean result = articleMapper.logicalDeleteByPrimaryKey(2);
        System.out.println(result);

    }

    @Test
    public void testPage() {

        PageHelper.startPage(2, 5);
        List<Article> list = articleMapper.selectAll();
        PageInfo page = new PageInfo(list);
        System.out.println(page.getList() == list);
        System.out.println(page.getTotal());

        PageHelper.startPage(3, 2);
        List articleList = articleMapper.getPage().stream().filter(p -> !p.getIsDelete() && p.getArticleId() > 1).collect(Collectors.toList());
        System.out.println(articleList);

    }

}
