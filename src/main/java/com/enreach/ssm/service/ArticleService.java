package com.enreach.ssm.service;

import com.enreach.ssm.infrastructure.PagedList;
import com.enreach.ssm.pojo.dto.ArticleDto;
import com.enreach.ssm.pojo.vo.ArticleVO;

import java.util.List;

public interface ArticleService {

    /**
     * 新增文章
     *
     * @param dto
     * @return
     */
    int add(ArticleDto dto);

    /**
     * detail
     * @param id
     * @return
     */
    ArticleVO detail(int id);

    /**
     * list
     * @param pageNum
     * @param pageSize
     * @return
     */
    PagedList<ArticleVO> list(int pageNum, int pageSize);

}
