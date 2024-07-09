package com.demo.mwm.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Common response structure")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    @Schema(description = "Indicates if the request was successful", example = "true")
    private Boolean success;

    @Schema(description = "Detailed message about the response", example = "Operation completed successfully")
    private String message;

    @Schema(description = "Response code indicating the result of the operation", example = "200")
    private Integer responseCode;

    @Schema(description = "The actual data returned in the response")
    private T data;

    public CommonResponse() {
    }

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

    public void setData(T data) {
        this.data = data;
    }
}
