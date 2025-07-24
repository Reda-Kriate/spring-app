package com.reda.auth;

public record AuthRequest(
        String username,
        String password
) {}
