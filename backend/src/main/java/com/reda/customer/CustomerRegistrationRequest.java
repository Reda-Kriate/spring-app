package com.reda.customer;

public record CustomerRegistrationRequest(
        String name, Integer age, String email, String password, String gender
){}
