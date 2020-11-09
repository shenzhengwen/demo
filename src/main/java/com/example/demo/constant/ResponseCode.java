/** Copyright  © Citic Trust, 2020 中信信托有限公司，版权所有  ©  2020 
 tae5Le2Choh5see2rieshaiQuooGhe7eeth0yahg3ud6eeb1ahsha6eloh2aiYai
*/



package com.example.demo.constant;

public enum ResponseCode {
    OK(0, "ok"),
    FAILED(-1, "failed"),
    ILLEGAL_PARAM(300001, "传入参数不合法"),
    DB_EXCEPTION(300002, "数据库异常"),
    UNKNOWN_EXCEPTION(300003, "未知异常"),
    DEMO_SERVICE_EXCEPTION(300003, "demo service 异常"),

    SEND_CONTRACT_FAIL(310001, "发送admin列表消息失败"),
    ;

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ResponseCode() {
    }
}
