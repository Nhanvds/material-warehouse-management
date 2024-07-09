package com.demo.mwm.service.exception;

import com.demo.mwm.config.Translator;
import com.demo.mwm.dto.response.CommonResponse;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public CommonResponse<?> handleCustomException(CustomException ex) {

        return new CommonResponse<>()
                .error()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .message(Translator.toLocale(ex.getMessage(), ex.getArgs()))
                .responseCode(ex.getErrorStatus().value());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errors = result.getFieldErrors().stream()
                .map(error -> Translator.toLocale(error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new CommonResponse<>()
                .error()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .message("Method Argument Not Valid")
                .data(errors);
    }

    @ExceptionHandler(value = {PathElementException.class})
    public CommonResponse<?> handlePathElementException(PathElementException ex) {
        return new CommonResponse<>()
                .error()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getLocalizedMessage());
    }
}
