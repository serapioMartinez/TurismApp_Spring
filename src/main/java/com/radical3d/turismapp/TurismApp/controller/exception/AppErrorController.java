package com.radical3d.turismapp.TurismApp.controller.exception;

import java.util.Enumeration;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.utils.LoggerHelper;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AppErrorController implements ErrorController {
    @RequestMapping("/error")
    ResponseEntity<ErrorResponse> requestMethodName(HttpServletRequest request) {
        
        Exception e = (Exception) request.getAttribute("jakarta.servlet.error.exception");
        int status = Integer.parseInt(request.getAttribute("jakarta.servlet.error.status_code").toString());
        String error_path = request.getAttribute("jakarta.servlet.error.request_uri").toString();
        if (e instanceof ResponseStatusException) {
            ResponseStatusException rse = (ResponseStatusException) e;

            return ResponseEntity
                    .status(rse.getStatusCode().value())
                    .body(ErrorResponse.builder()
                            .error(rse.getStatusCode())
                            .message(rse.getReason())
                            .status(rse.getStatusCode().value())
                            .path(error_path)
                            .build());
        }
        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .error(HttpStatusCode.valueOf(status))
                        .message(e.getMessage())
                        .status(status)
                        .path(error_path)
                        .build());
    }

}
