package com.reda.security;

import com.reda.JWT.JwtAuthentificationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthentificationFilter jwtAuthentificationFilter;

    public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider, JwtAuthentificationFilter jwtAuthentificationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthentificationFilter = jwtAuthentificationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(csrf -> csrf.disable()) //Désactive la protection CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/customer").permitAll()//La route POST /api/v1/customer est publique.
                        .anyRequest()
                        .authenticated()) // Toutes les autres requêtes doivent être authentifiées.
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configure la politique de session (stateless car JWT)
                )
                .authenticationProvider(authenticationProvider) //Mon DaoAuthenticationProvider personnalisé
                .addFilterBefore(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class) //Injecte ton JwtAuthenticationFilter avant l’authentification par username/password
                .build();
    }
}

