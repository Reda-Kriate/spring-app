package com.reda.security;

import com.reda.JWT.JwtAuthenticationFilter;
import com.reda.customer.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(csrf -> csrf.disable()) //Désactive la protection CSRF
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/customer","/api/v1/auth/login").permitAll()//La route POST /api/v1/customer est publique.
                        .anyRequest()
                        .authenticated()) // Toutes les autres requêtes doivent être authentifiées.
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configure la politique de session (stateless car JWT)
                )
                .authenticationProvider(authenticationProvider) //Mon DaoAuthenticationProvider personnalisé
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) //Injecte ton JwtAuthenticationFilter avant l’authentification par username/password
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint(authenticationEntryPoint))
                .build();
    }
}

