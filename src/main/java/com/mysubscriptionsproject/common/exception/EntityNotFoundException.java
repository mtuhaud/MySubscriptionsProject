package com.mysubscriptionsproject.common.exception;

public class EntityNotFoundException  extends RuntimeException {

    public EntityNotFoundException(Long id) {
        super("Entity not found: " + id);
    }
}
