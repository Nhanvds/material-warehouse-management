package com.demo.mwm.exception;

import com.demo.mwm.component.Translator;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.sqm.PathElementException;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullArgumentException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 *
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LogManager.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<String> handleCustomException(CustomException ex) {

        String errorMessage;
        try {
            errorMessage = Translator.toLocale(ex.getMessage(), ex.getArgs());
        } catch (NoSuchMessageException exc) {
            errorMessage = ex.getMessage();
        }
        logger.info(errorMessage);
        return ResponseEntity
                .status(ex.getErrorStatus())
                .body(errorMessage);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errors = result.getFieldErrors().stream()
                .map(error -> Translator.toLocale(error.getDefaultMessage()))
                .toList();
        StringBuilder errorString = new StringBuilder();
        errors.forEach(errorString::append);
        logger.warn(errorString.toString());
        return ResponseEntity.badRequest()
                .body(errorString.toString());
    }

    @ExceptionHandler(value = {PathElementException.class})
    public ResponseEntity<String> handlePathElementException(PathElementException ex) {
        logger.warn(ex.getLocalizedMessage());
        return ResponseEntity.badRequest()
                .body(ex.getLocalizedMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.warn(ex.getLocalizedMessage());
        return ResponseEntity.badRequest()
                .body(Translator.toLocale(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(value = {NullArgumentException.class})
    public ResponseEntity<String> handleNullArgumentException(NullArgumentException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.badRequest()
                .body(ex.getLocalizedMessage());
    }

}
