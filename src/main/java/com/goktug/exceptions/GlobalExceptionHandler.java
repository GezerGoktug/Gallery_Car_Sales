package com.goktug.exceptions;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

//! Controller da error ları yakalar .Error middleware gibi
@ControllerAdvice
public class GlobalExceptionHandler {

    // ! Normal hatalar için bu kullanılır
    @ExceptionHandler(value = { BaseException.class })
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(createApiError(ex.getMessage(), ex.getStatusCode(), request));
    }

    // ! Spring validation hataları için kullanılır
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleBaseException(MethodArgumentNotValidException ex,
            WebRequest request) {

        // ? error:["","",""] tarzı
        Map<String, List<String>> hashMap = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();

            if (hashMap.containsKey(fieldName)) {
                hashMap.put(fieldName, addToList(hashMap.get(fieldName), ((FieldError) err).getDefaultMessage()));
            } else {
                hashMap.put(fieldName, addToList(new ArrayList<>(), ((FieldError) err).getDefaultMessage()));
            }
        });

        return ResponseEntity.badRequest().body(createApiError(hashMap, ex.getStatusCode().value(), request));
    }

    private List<String> addToList(List<String> list, String value) {
        list.add(value);
        return list;
    }

    private String getLocalHost() {
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    public <E> ApiError<E> createApiError(E message, Integer statusCode, WebRequest request) {
        ApiError<E> apiError = new ApiError<E>();

        Exception<E> exception = new Exception<>();

        apiError.setStatus(statusCode);

        exception.setCreatedAt(new Date());
        exception.setMessage(message);
        exception.setPath(request.getDescription(false).substring(4));
        exception.setHostName(getLocalHost());

        apiError.setException(exception);

        return apiError;

    }
}
