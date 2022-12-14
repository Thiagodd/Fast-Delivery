package com.fastdelivery.fastdelivery.domain.service.exceptions;

import java.io.Serial;

public class EntityInUseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1896954078708767707L;

    public EntityInUseException(String message) {
        super(message);
    }
}
