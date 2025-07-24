package com.reda.auth;

import com.reda.JWT.JWTUtil;
import com.reda.customer.Customer;
import com.reda.customer.CustomerDTO;
import com.reda.customer.CustomerDTOMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomerDTOMapper customerDTOMapper;
    private final JWTUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, CustomerDTOMapper customerDTOMapper, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customerDTOMapper = customerDTOMapper;
        this.jwtUtil = jwtUtil;
    }
    public AuthResponse login(AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.username(),
                        authRequest.password()
                )
        );
        Customer principal = (Customer) authenticate.getPrincipal();
        CustomerDTO customerDTO = customerDTOMapper.apply(principal);
        String token = jwtUtil.issueToken(customerDTO.username(), customerDTO.roles());
        return new AuthResponse(token , customerDTO);
    }
}
