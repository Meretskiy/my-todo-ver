package com.meretskiy.mytodover.exceptions_handling;

import lombok.Data;

import java.util.Date;

@Data
public class TodoverError {

    private int status;
    private String message;
    private Date timestamp;

    public TodoverError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
