package com.enreach.ssm.dao;

import java.util.Date;

import com.enreach.ssm.entity.Article;
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

        Article article = new Article();
        article.setTitle("no12");
        article.setCreateTime(new Date());
        article.setCreator("lizhi");
        article.setContext("Mybatis-generator(MBG)是Mybatis官网发布的一个用来生成代码的一个工具包，他可以对一些简单的CRUD应用进行快速的生成POJO对象和对应的Mapper接口文件及XML配置文件，大大的减少了手写的误差和工作量");

        articleMapper.insertSelective(article);

        //LOG.debug(article.getTitle());

        System.out.println(article.getArticleId());

    }

    @Test
    public void testExample() {

        Example example =new Example(Article.class);
        example.createCriteria().andEqualTo("articleId",22);

        articleMapper.selectByExample(example);
        articleMapper.selectByExample(example);

        articleMapper.getOneById(12);
        articleMapper.getOneById(12);

        //ArticleExample example = new ArticleExample();
        //example.createCriteria().andTitleLike("%no%").andIsDeleteEqualTo(false);
        //example.setOrderByClause("articleId desc");
        //
        //Article article = articleMapper.selectOneByExampleWithBLOBs(example);
        //
        //System.out.println(article);

      //  articleMapper.logicalDeleteByPrimaryKey(1);

        // articleMapper.selectMore();
    }

}
