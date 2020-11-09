/** Copyright  © Citic Trust, 2020 中信信托有限公司，版权所有  ©  2020 
 tae5Le2Choh5see2rieshaiQuooGhe7eeth0yahg3ud6eeb1ahsha6eloh2aiYai
*/



package com.example.demo.exception;


import com.example.demo.constant.ResponseCode;
import lombok.Data;

@Data
public class DemoException extends RuntimeException {

    private ResponseCode responseCode;
    private String errorMsg;

    private int code;

    private String msg;

    public DemoException(String errorMsg) {
        super(errorMsg);
    }

    public DemoException(ResponseCode responseCode) {
        super(responseCode.getMsg());
        this.responseCode = responseCode;
        this.errorMsg = responseCode.getMsg();
    }

    public DemoException(String code, String msg) {
        super(msg);
        this.code = Integer.valueOf(code);
        this.msg = msg;
        this.errorMsg = msg;
    }

    public DemoException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.errorMsg = msg;
    }

}
