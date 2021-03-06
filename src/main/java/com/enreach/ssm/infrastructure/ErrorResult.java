package com.enreach.ssm.infrastructure;

public class ErrorResult {
    private int code;
    private String error;
    private Object data;

    public ErrorResult(int code, String error, Object data) {
        this.code = code;
        this.error = error;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
