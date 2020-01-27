package com.depanker.orderandsales.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapper {
    Object response;
    public ResponseWrapper(Object data) {
        response = data;
    }
}
