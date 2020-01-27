package com.depanker.orderandsales.web.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends Exception {
    private final String message;
    public AppException(String message) {
        this.message =  message;
    }
}
