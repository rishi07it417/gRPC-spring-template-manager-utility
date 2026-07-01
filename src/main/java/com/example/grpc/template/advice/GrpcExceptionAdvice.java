package com.example.grpc.template.advice;

import io.grpc.Status;
import org.springframework.grpc.server.advice.GrpcAdvice;
import org.springframework.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class GrpcExceptionAdvice {


    @GrpcExceptionHandler
    public Status handleIllegalArgumentException(IllegalArgumentException ex) {
        return Status.INVALID_ARGUMENT
                .withDescription(ex.getMessage());
    }

    @GrpcExceptionHandler
    public Status handleGenericException(Exception ex) {
        return Status.INTERNAL
                .withDescription("An unexpected error occurred: " + ex.getMessage());
    }
}