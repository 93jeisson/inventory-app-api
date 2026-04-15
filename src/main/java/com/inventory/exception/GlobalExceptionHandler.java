package com.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> manejarError(RuntimeException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("mensaje", ex.getMessage());
        error.put("status", 400);
        error.put("fecha", LocalDate.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}