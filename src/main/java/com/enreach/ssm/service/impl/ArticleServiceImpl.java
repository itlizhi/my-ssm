package com.enreach.ssm.service.impl;

import com.enreach.ssm.dao.ArticleMapper;
import com.enreach.ssm.dao.ArticleTagMapper;
import com.enreach.ssm.dao.TagMapper;
import com.enreach.ssm.dto.ArticleDto;
import com.enreach.ssm.service.ArticleService;
import com.enreach.ssm.infrastructure.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());


    @Transactional(rollbackFor = BizException.class)
    @Override
    public int add(ArticleDto dto) {




        return 0;
    }
}
