package com.enreach.ssm.web;

import com.enreach.ssm.pojo.dto.ArticleDto;
import com.enreach.ssm.pojo.vo.ArticleVO;
import com.enreach.ssm.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public int add(ArticleDto dto) {
        return articleService.add(dto);

    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ArticleVO detail(@PathVariable int id) {
        LOG.debug("id:" + id);
        return articleService.detail(id);
    }


    public List<ArticleVO> list(int pageNum, @RequestParam(name = "pageSize", defaultValue = "20", required = false) int pageSize) {
        return null;
    }

    @RequestMapping("/msg")
    @ResponseBody
    public String msg() {
        return "中文测试";
    }
}
