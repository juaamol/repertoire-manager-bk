package com.learning.repertoire_manager.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserContext {

    public UUID getCurrentUserId() {
        // Principal == UUID from JWT filter
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UUID uuid) {
            return uuid;
        } else {
            throw new IllegalStateException("No authenticated user found");
        }
    }
}
