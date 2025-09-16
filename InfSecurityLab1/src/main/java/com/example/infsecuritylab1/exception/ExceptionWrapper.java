package com.example.infsecuritylab1.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Slf4j
public class ExceptionWrapper {
    private String message;
    private LocalDateTime time;

    public ExceptionWrapper(Exception e){
        log.debug("exception wrapper got error: ", e);
        message = e.getMessage();
        time = LocalDateTime.now();
    }
}
