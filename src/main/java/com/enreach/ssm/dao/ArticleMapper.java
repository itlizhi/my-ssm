package com.enreach.ssm.dao;

import com.enreach.ssm.entity.Article;
import com.enreach.ssm.entity.example.ArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper {
    /**
    * This method was generated by MyBatis Generator. database table [t_article]
     */
    long countByExample(ArticleExample example);

    /**
    * This method was generated by MyBatis Generator. database table [t_article]
     */
    int deleteByExample(ArticleExample example);

    /**
    * This method was generated by MyBatis Generator. database table [t_article]
     */
    int insert(Article record);

    /**
    * This method was generated by MyBatis Generator. database table [t_article]
     */
    int insertSelective(Article record);

    /**
    * This method was generated by MyBatis Generator. database table [t_article]
     */
    Article selectOneByExample(ArticleExample example);

    /**
    * This method was generated by MyBatis Generator. database table [t_article]
     */
    Article selectOneByExampleWithBLOBs(ArticleExample example);

    /**
    * This method was generated by MyBatis Generator. database table [t_article]
     */
    List<Article> selectByExampleWithBLOBs(ArticleExample example);

    /**
    * This method was generated by MyBatis Generator. database table [t_article]
     */
    List<Article> selectByExample(ArticleExample example);

    /**
    * This method was generated by MyBatis Generator. database table [t_article]
     */
    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);

    /**
    * This method was generated by MyBatis Generator. database table [t_article]
     */
    int updateByExampleWithBLOBs(@Param("record") Article record, @Param("example") ArticleExample example);

    /**
    * This method was generated by MyBatis Generator. database table [t_article]
     */
    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);
}