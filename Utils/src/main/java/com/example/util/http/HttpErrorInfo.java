package com.example.util.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter
public class HttpErrorInfo {
    private ZonedDateTime timestamp;
    private String path;
    private HttpStatus httpStatus;
    private String message;

    public HttpErrorInfo(HttpStatus httpStatus, String path, String message) {
        this.timestamp = ZonedDateTime.now();
        this.httpStatus = httpStatus;
        this.path = path;
        this.message = message;
    }
}

