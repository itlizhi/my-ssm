package com.enreach.ssm.infrastructure;


/**
 * 全局自定义异常
 */

public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }


}
