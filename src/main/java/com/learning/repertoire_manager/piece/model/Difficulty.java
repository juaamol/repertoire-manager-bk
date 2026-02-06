package com.learning.repertoire_manager.piece.model;

import java.util.Arrays;

public enum Difficulty {
    BEGINNER, INTERMEDIATE, ADVANCED;

    public static Difficulty fromString(String value) {
        try {
            return Difficulty.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Invalid difficulty. Allowed values: " + Arrays.toString(Difficulty.values())
            );
        }
    }
}
