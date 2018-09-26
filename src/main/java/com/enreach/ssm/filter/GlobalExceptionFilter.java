package com.enreach.ssm.filter;

import com.enreach.ssm.infrastructure.BizException;
import com.enreach.ssm.infrastructure.ErrorResult;
import com.enreach.ssm.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局错误处理
 */
@Component
public class GlobalExceptionFilter implements HandlerExceptionResolver {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    @ResponseBody
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {

            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            PrintWriter writer = response.getWriter();
            ErrorResult errorResult = null;
            //业务错误
            if (ex instanceof BizException) {
                int code = 400;
                response.setStatus(400);
                BizException bizException = (BizException) ex;
                if (bizException.getErrorEnum() != null) {
                    code = bizException.getErrorEnum().getState();
                }
                errorResult = new ErrorResult(code, ex.getMessage(), bizException.getData());
            } else {
                //系统未处理错误
                response.setStatus(500);
                errorResult = new ErrorResult(500, "系统错误", null);
                LOG.error("访问:" + request.getRequestURI() + " 发生错误, 错误信息:" + ex.getMessage());
                ex.printStackTrace();
            }
            writer.write(JsonUtil.serialize(errorResult));
            writer.flush();
            writer.close();//必须close，不然client不会得到response数据

        } catch (Exception e) {
            LOG.error("GlobalExceptionFilter：", e);
        }

        return null;


        //跳转到定制化的错误页面
	    /*ModelAndView error = new ModelAndView("error");
		error.addObject("exMsg", ex.getMessage());
		error.addObject("exType", ex.getClass().getSimpleName().replace("\"", "'"));*/

    }

}
