package com.adminbackend.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public class AppException extends Exception{

    @Getter
    private final HttpStatus statusCode;

    public AppException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;
    }


}
