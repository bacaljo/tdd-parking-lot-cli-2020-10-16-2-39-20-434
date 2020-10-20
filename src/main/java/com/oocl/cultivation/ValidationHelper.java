package com.oocl.cultivation;

public final class ValidationHelper {
    private ValidationHelper() {
    }

    public static void validate(boolean condition, RuntimeException exception) {
        if (!condition) {
            throw exception;
        }
    }
}
