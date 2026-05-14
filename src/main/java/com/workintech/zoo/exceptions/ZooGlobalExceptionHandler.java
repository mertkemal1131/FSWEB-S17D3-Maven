package com.workintech.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
@Slf4j
public class ZooGlobalExceptionHandler {

    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ZooErrorResponse> handleZooException(ZooException ex) {
        log.error("ZooException: {}", ex.getMessage());

        ZooErrorResponse response = new ZooErrorResponse(
                ex.getHttpStatus().value(),     // status (int)
                ex.getMessage(),                // message (String)
                Instant.now().toEpochMilli()    // timestamp (long)
        );

        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ZooErrorResponse> handleGeneralException(Exception ex) {
        log.error("Unexpected error: ", ex);

        ZooErrorResponse response = new ZooErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),   // status
                "Internal Server Error",                     // message
                Instant.now().toEpochMilli()                 // timestamp
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}