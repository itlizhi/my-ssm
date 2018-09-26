package com.enreach.ssm.web;

import com.enreach.ssm.infrastructure.PagedList;
import com.enreach.ssm.pojo.dto.ArticleDto;
import com.enreach.ssm.pojo.vo.ArticleVO;
import com.enreach.ssm.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleService articleService;

    /**
     * 参数 @requestBody 适合ajax提交
     * 不加requestBody 默认使用application/x-www-form-urlencoded ，对数组的处理要arr[0]=1&arr[1]=2
     * <p>
     * 如果要验证@Valid，方法上必须加上BindingResult result，不然BindingResultFilter aop获取不到这个参数
     *
     * @param dto
     * @return
     */

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public int add(@RequestBody @Valid ArticleDto dto, BindingResult result) {
        LOG.debug(dto.toString());
        return articleService.add(dto);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ArticleVO detail(@PathVariable int id) {
        LOG.debug("id:" + id);
        return articleService.detail(id);
    }

    @RequestMapping("/list")
    @ResponseBody
    public PagedList<ArticleVO> list(int pageNum, @RequestParam(name = "pageSize", defaultValue = "20", required = false) int pageSize) {
        LOG.debug("p:" + pageNum + "; s:" + pageSize);
        return articleService.list(pageNum, pageSize);
    }

    @RequestMapping("/msg")
    @ResponseBody
    public String msg() {
        return "中文内容字符串";
    }


    @RequestMapping("/index")
    public String index(Model model) {

        model.addAttribute("message", "中文内容字符串");
        model.addAttribute("time",new Date());
        model.addAttribute("bool",true);
        model.addAttribute("article",detail(35));
        model.addAttribute("array",        Arrays.asList("a","b","c"));

        return "index";
    }

    @RequestMapping("/vue")
    public String vue(Model model) {
        model.addAttribute("article",detail(35));
        model.addAttribute("array",        Arrays.asList("a","b","c"));
        return "vue";
    }
}
