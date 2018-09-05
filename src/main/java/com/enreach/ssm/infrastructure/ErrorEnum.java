package com.enreach.ssm.infrastructure;

public enum ErrorEnum {

    INNER_ERROR(500, "InternalServerError"),
    REQUEST_ERROR(400, "bad request"),
    UNAUTHORIZED(401, "unauthorized"),

    /**
     * 在下面定义错误code
     */

    DB_ERROR(9000, "db error"),

    MODEL_STATE_ERROR(8999, "参数错误");

    private int state;

    private String msg;

    ErrorEnum(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public static ErrorEnum stateOf(int index) {
        for (ErrorEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

}
