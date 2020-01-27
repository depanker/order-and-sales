package com.depanker.orderandsales.exceptionhandler;

import com.depanker.orderandsales.bean.ResponseWrapper;
import com.depanker.orderandsales.web.exception.AppException;
import com.depanker.orderandsales.web.exception.InputValidation;
import com.depanker.orderandsales.web.exception.NoContentException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class ExceptionAdvice implements ResponseBodyAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<InputValidation> handleBadInputException(MethodArgumentNotValidException e, HandlerMethod handlerMethod) {
        List<InputValidation> errors =  e.getBindingResult().getFieldErrors().stream()
                .map(error -> new InputValidation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        log.warn("Input validation error occurred while request from {}.{}(), error: {}", handlerMethod.getBeanType()
                .getSimpleName(), handlerMethod.getMethod().getName(), errors.toString());
        return errors;
    }
    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<InputValidation> handleBadInputException(MissingServletRequestPartException e, HandlerMethod handlerMethod) {
        List<InputValidation> errors = Arrays.asList(new InputValidation(e.getRequestPartName(), e.getMessage()));
        log.warn("Input validation error occurred while request from {}.{}(), error: {}", handlerMethod.getBeanType()
                .getSimpleName(), handlerMethod.getMethod().getName(), errors.toString());
        return errors;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(CONFLICT)
    @ResponseBody
    void dataAlreadyExists(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler(CommandAcceptanceException.class)
    @ResponseStatus(CONFLICT)
    @ResponseBody
    void dataAlreadyExists(CommandAcceptanceException e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    void notDataFound(NoContentException e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(FORBIDDEN)
    @ResponseBody
    void notDataFound(AppException e) {
        log.error(e.getMessage(), e);
    }


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body != null) {
            return new ResponseWrapper(body);
        } else {
            return null;
        }
    }
}
