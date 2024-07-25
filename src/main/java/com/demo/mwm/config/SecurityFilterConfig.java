package com.demo.mwm.config;

import com.demo.mwm.entity.PermissionEntity;
import com.demo.mwm.service.IPermissionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static com.demo.mwm.utils.AuthoritiesConstants.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;
    private final IPermissionService permissionService;

    public SecurityFilterConfig(AuthenticationProvider authenticationProvider, JwtAuthFilter jwtAuthFilter, IPermissionService permissionService) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
        this.permissionService = permissionService;
    }

    private static final String SUFFIXES = "/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        req -> {
                            for (String endpoint : WHITE_LISTED) {
                                req.requestMatchers(endpoint + SUFFIXES).permitAll();
                            }
                            for (PermissionEntity permission : permissionService.getAll()) {
                                req.requestMatchers(permission.getMethod().name(), permission.getEndpoint())
                                        .hasAuthority(permission.getName());
                            }
                            req.anyRequest().authenticated();
                        }
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .build();

    }
}
