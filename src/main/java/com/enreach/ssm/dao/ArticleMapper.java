package com.enreach.ssm.dao;

import com.enreach.ssm.entity.Article;
import com.enreach.ssm.util.IMapper;

public interface ArticleMapper extends IMapper<Article> {
    Article getOneById(int id);
}