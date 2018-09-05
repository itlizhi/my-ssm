package com.enreach.ssm.filter;

import com.enreach.ssm.infrastructure.BizException;
import com.enreach.ssm.infrastructure.ErrorEnum;
import com.enreach.ssm.utils.CheckUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用 aop 的方式对 数据进行校验
 */
@Component
@Aspect
public class BindingResultFilter {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.enreach.ssm.web.*.*(..))")
    public void aopMethod() {
    }

    @Around("aopMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        BindingResult bindingResult = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                bindingResult = (BindingResult) arg;
            }
        }
        if (bindingResult != null && bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorList.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
            }

            if (CheckUtil.isNotEmpty(errorList)) {
                throw new BizException(ErrorEnum.MODEL_STATE_ERROR, errorList);
            }
        }
        return joinPoint.proceed();
    }
}

