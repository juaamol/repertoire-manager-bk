package com.learning.repertoire_manager.works.model;

import java.util.Arrays;

import com.learning.repertoire_manager.works.model.Status;

public enum Status {
    PLANNED, LEARNING, REFINING, POLISHING, MASTERED, ARCHIVED;

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
