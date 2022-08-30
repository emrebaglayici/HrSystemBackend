package com.emrebaglayici.myhremrebaglayici.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class InterviewFailException extends RuntimeException{
    public InterviewFailException(String message) {
        super(message);
    }
}
