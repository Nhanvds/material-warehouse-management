package com.demo.mwm.service.exception;

import com.demo.mwm.service.dto.response.CommonResponse;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public CommonResponse<?> handleCustomException(CustomException ex) {
        return new CommonResponse<>()
                .error()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .responseCode(ex.getErrorStatus().value());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return new CommonResponse<>()
                .error()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .message("Method Argument Not Valid")
                .data(result.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()));
    }

    @ExceptionHandler(value = {PathElementException.class})
    public CommonResponse<?> handlePathElementException(PathElementException ex) {
        return new CommonResponse<>()
                .error()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getLocalizedMessage());
    }
}
