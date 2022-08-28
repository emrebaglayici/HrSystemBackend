package com.emrebaglayici.myhremrebaglayici.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_EXTENDED)
public class FillTheBlanks extends RuntimeException {
    public FillTheBlanks(String message){
        super(message);
    }
}
