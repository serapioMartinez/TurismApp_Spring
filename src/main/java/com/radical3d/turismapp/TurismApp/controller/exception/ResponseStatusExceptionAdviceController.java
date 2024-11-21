package com.radical3d.turismapp.TurismApp.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.utils.LoggerHelper;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice()
public class ResponseStatusExceptionAdviceController {
    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<ErrorResponse> responseExceptionHandler(ResponseStatusException e, HttpServletRequest request){
        LoggerHelper.error(e.getMessage()+"\t"+request.getRequestURI());
        e.printStackTrace(LoggerHelper.printWriter);
        ErrorResponse errorResponse = ErrorResponse.builder()
                                            .status(e.getStatusCode().value())
                                            .error(e.getStatusCode())
                                            .message(e.getReason())
                                            .path(request.getRequestURI())
                                            .build();
        
        return ResponseEntity.ofNullable(errorResponse);
    }
}
