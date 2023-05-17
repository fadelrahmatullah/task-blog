package com.task.blog.taskblog.handler;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.blog.taskblog.common.exception.ValidationException;
import com.task.blog.taskblog.common.model.Response;
import com.task.blog.taskblog.common.model.ResponseStatus;

import jakarta.annotation.Nullable;

@ControllerAdvice
public class RestResponseExceptionHandler {

    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        Response<Object> response = new Response<>(ResponseStatus.ERROR, new Date());
        response.setMessage(ex.getMessage());

        String body = null;
        try {
            body = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException ex2) {
            body = ex2.getMessage();
        }

        return handleExceptionInternal(ex, body, header, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleExceptionGlobal(Exception ex, WebRequest request) {
        

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        Response<Object> response = new Response<>(ResponseStatus.ERROR, new Date());
            response.setMessage(ex.getMessage());
            response.setData(null);

        String body = null;
        try {
            body = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException ex2) {
            body = ex2.getMessage();
        }

        return handleExceptionInternal(ex, body, header, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        Response<Object> response = new Response<>(ResponseStatus.ERROR, new Date());
        response.setMessage(String.join(", ", ex.getMessage()));
        response.setData(null);
        String body = null;
        try {
            body = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException ex2) {
            body = ex2.getMessage();
        }

        return handleExceptionInternal(ex, body, header, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    protected ResponseEntity<Object> handleBindException(BindException ex, WebRequest request) {

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        Response<Object> response = new Response<>(ResponseStatus.ERROR, new Date());
        
        if (ex.getBindingResult().getAllErrors().size() > 0) {
            
            response.setMessage(String.join(", ", ex.getBindingResult()
            .getAllErrors().stream()
            .map(t -> t.getDefaultMessage()).collect(Collectors.toList())));
            String body = null;
            try {
                body = objectMapper.writeValueAsString(response);
            } catch (JsonProcessingException ex2) {
                body = ex2.getMessage();

            }
            
             return handleExceptionInternal(ex, body, header, HttpStatus.BAD_REQUEST, request);
        }else{

            response.setMessage(ex.getMessage());
            response.setData(null);

            String body = null;
            try {
                body = objectMapper.writeValueAsString(response);
            } catch (JsonProcessingException ex2) {
                body = ex2.getMessage();
            }
            return handleExceptionInternal(ex, body, header, HttpStatus.BAD_REQUEST, request);
        }
       
    }

    @ExceptionHandler(value = {ResponseStatusException.class})
    protected ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        Response<Object> response = new Response<>(ResponseStatus.ERROR, new Date());
        response.setMessage(ex.getReason());

        String body = null;
        try {
            body = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException ex2) {
            body = ex2.getMessage();
        }

        return handleExceptionInternal(ex, body, header, ex.getStatusCode(), request);
    }
    
     protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers
            , HttpStatusCode status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
