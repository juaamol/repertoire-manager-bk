package com.learning.repertoire_manager.model;

import java.util.Arrays;

public enum Status {
    PLANNED, LEARNING, POLISHING, MASTERED, READY;

    public static Status fromString(String value) {
        try {
            return Status.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Invalid status. Allowed values: " + Arrays.toString(Status.values())
            );
        }
    }
}
