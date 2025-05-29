package org.csf.cat.exception;

public class CatNotFoundException extends RuntimeException {
    public CatNotFoundException(String id) {
        super("Cat not found with id: " + id);
    }
}
