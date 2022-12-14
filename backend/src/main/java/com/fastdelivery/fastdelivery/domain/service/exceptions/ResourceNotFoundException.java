package com.fastdelivery.fastdelivery.domain.service.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -8045426600829529157L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
