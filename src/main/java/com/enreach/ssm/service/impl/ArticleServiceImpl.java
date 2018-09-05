package com.enreach.ssm.service.impl;

import java.util.Date;

import com.enreach.ssm.dao.ArticleMapper;
import com.enreach.ssm.dao.ArticleTagMapper;
import com.enreach.ssm.dao.TagMapper;
import com.enreach.ssm.pojo.dto.ArticleDto;
import com.enreach.ssm.entity.Article;
import com.enreach.ssm.entity.ArticleTag;
import com.enreach.ssm.entity.Tag;
import com.enreach.ssm.pojo.vo.ArticleVO;
import com.enreach.ssm.service.ArticleService;
import com.enreach.ssm.infrastructure.BizException;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private TagServiceImpl tagService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());


    /**
     * 跨 service 事务是支持的，直接在调用方法上加入Transactional
     * 注意：调用的其他service的方法不得加上Transactional，否则会出现嵌套依赖异常
     *
     * @param dto
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int add(ArticleDto dto) {

        if (StringUtils.isBlank(dto.getTitle())) {
            throw new BizException("文章标题错误");
        }

        ModelMapper modelMapper = new ModelMapper();
        Article article = modelMapper.map(dto, Article.class);
        article.setCreateTime(new Date());
        article.setCreator("lizhi");

        articleMapper.insertSelective(article);


        List<Tag> tags = new ArrayList<>();
        if (dto.getTags() != null && dto.getTags().length > 0) {
            for (String tag : dto.getTags()) {
                //调用其他 service 方法
                tags.add(tagService.findOrAddByName(tag));
            }
        }

        for (Tag tag : tags) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getArticleId());
            articleTag.setTagId(tag.getTagId());
            articleTag.setCreateTime(new Date());
            articleTag.setCreator("lizhi");
            articleTagMapper.insertSelective(articleTag);
        }

        LOG.debug(article.toString());

        return article.getArticleId();
    }

    @Override
    public ArticleVO detail(int id) {

        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        ArticleVO vo = modelMapper.map(article, ArticleVO.class);
        return vo;
    }

    @Override
    public List<ArticleVO> list(int pageNum, int pageSize) {

        return null;
    }
}
