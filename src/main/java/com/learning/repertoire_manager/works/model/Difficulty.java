package com.learning.repertoire_manager.works.model;

import java.util.Arrays;

import com.learning.repertoire_manager.works.model.Difficulty;

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
