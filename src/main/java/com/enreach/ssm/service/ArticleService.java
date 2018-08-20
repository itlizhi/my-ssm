package com.enreach.ssm.service;

import com.enreach.ssm.dto.ArticleDto;

public interface ArticleService {

    /**
     * 新增文章
     *
     * @param dto
     * @return
     */
    int add(ArticleDto dto);

}
