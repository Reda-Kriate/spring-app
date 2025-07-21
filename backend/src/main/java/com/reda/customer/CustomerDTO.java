package com.reda.customer;

import java.util.List;

public record CustomerDTO(
        Integer id,
        String name,
        Integer age,
        String email,
        String gender,
        List<String> roles,
        String username
) {}
