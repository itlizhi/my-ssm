package com.enreach.ssm.infrastructure;


import javafx.concurrent.Task;

/**
 * 全局自定义异常
 */

public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Object data;

    private ErrorEnum errorEnum;

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public Object getData() {
        return data;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(ErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.errorEnum = errorEnum;
    }

    public BizException(ErrorEnum errorEnum, Object data) {
        super(errorEnum.getMsg());
        this.errorEnum = errorEnum;
        this.data = data;
    }


    public BizException(String message, Throwable cause) {
        super(message, cause);
    }


}
