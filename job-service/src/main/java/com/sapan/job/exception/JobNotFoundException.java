package com.sapan.job.exception;

public class JobNotFoundException
        extends RuntimeException {

    public JobNotFoundException(String message) {
        super(message);
    }
}