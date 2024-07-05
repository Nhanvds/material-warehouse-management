package com.vsii.mwm.service.dto.response;

public class PageResponse<T> extends CommonResponse<T> {

    private long dataCount;


    public PageResponse() {
    }

    public PageResponse(long dataCount) {
        this.dataCount = dataCount;
    }
    public PageResponse<T> dataCount(long dataCount){
        this.dataCount = dataCount;
        return this;
    }

    public long getDataCount() {
        return dataCount;
    }

    public void setDataCount(long dataCount) {
        this.dataCount = dataCount;
    }

    @Override
    public PageResponse<T> success() {
        super.success();
        return this;
    }

    @Override
    public PageResponse<T> message(String message) {
        super.message(message);
        return this;
    }

    @Override
    public PageResponse<T> responseCode(Integer code) {
        super.responseCode(code);
        return this;
    }

    @Override
    public PageResponse<T> error() {
        super.error();
        return this;
    }

    @Override
    public PageResponse<T> data(T data) {
        super.data(data);
        return this;
    }


}
