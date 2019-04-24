package com.circlee7.test.advice;

import com.circlee7.test.util.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionAdvice{

    @ExceptionHandler(CustomException.class)
    ResponseEntity<String> handleException(CustomException exception, HttpServletRequest req, HttpServletResponse res) {
        return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
    }

}
