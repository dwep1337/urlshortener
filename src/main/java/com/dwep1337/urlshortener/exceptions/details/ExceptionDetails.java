package com.dwep1337.urlshortener.exceptions.details;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected String title;
    protected int status;
    protected String detail;
    protected String developerMessage;
    protected LocalDateTime timestamp;
}