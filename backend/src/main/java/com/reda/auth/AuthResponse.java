package com.reda.auth;

import com.reda.customer.CustomerDTO;

public record AuthResponse(
        String token ,
        CustomerDTO customerDTO
) {}
