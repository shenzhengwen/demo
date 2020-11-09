/** Copyright  © Citic Trust, 2020 中信信托有限公司，版权所有  ©  2020 
 tae5Le2Choh5see2rieshaiQuooGhe7eeth0yahg3ud6eeb1ahsha6eloh2aiYai
*/



package com.example.demo.entity.dto;

import com.example.demo.constant.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 4775582366583523461L;

    private int code = 0;

    private String message = "";

    private T result;

    public void setCodeAndMessage(ResponseCode responseCode){
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg();
    }

    public void setProperties(ResponseCode responseCode, T result){
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg();
        this.result = result;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
