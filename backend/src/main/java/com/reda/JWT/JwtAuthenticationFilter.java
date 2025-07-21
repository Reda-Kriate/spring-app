package com.reda.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import reactor.util.annotation.NonNull;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        //Extraire depuis Header request la partie Authorization qui contient Token
        String authHeader = request.getHeader("Authorization");
        //si il n'existe pas de Token la requete continue sans verification
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        //supprimer les 7 premieres caracteres qui contient Bearer+esp
        //pour contenir seulement token
        String jwt = authHeader.substring(7);
        //utilise jwtUtil.getSubject(jwt) pour obtenir le nom d’utilisateur (souvent email ou username)
        String subject = jwtUtil.getSubject(jwt);

        if(subject != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            //if Token Authorization est valide
            if(jwtUtil.isTokenValid(jwt,userDetails.getUsername())){

                //Cree Token d'Authentication
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                //build details
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //creer le context de security pour Token d'Athentication
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        System.out.println("JwtAuthentificationFilter intercepting: " + request.getRequestURI());
        filterChain.doFilter(request,response);
    }
}
