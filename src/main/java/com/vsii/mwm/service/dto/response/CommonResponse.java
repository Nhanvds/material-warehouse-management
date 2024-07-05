package com.vsii.mwm.service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private Boolean success;
    private String message;
    private Integer responseCode;
    private T data;

    public CommonResponse() {}

    public CommonResponse<T> success() {
        this.success = true;
        return this;
    }

    public CommonResponse<T> message(String message) {
        this.message = message;
        return this;
    }

    public CommonResponse<T> responseCode(Integer code) {
        this.responseCode = code;
        return this;
    }

    public CommonResponse<T> error() {
        this.success = false;
        return this;
    }

    public CommonResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data){
        this.data = data;
    }
}
