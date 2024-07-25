package com.demo.mwm.config;

import com.demo.mwm.utils.AuthoritiesConstants;
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
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.DELETE;
import static com.demo.mwm.utils.AuthoritiesConstants.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityFilterConfig(AuthenticationProvider authenticationProvider, JwtAuthFilter jwtAuthFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/auth/*").permitAll()
                                .requestMatchers("/swagger-ui-custom.html/**").permitAll()
                                .requestMatchers(GET,"/materials/get-list").hasAnyRole(ADMIN,USER)
                                .requestMatchers(POST,"/materials/save").hasAnyAuthority(ADMIN_CREATE)
                                .requestMatchers(DELETE,"/materials/*/delete").hasAnyAuthority(ADMIN_DELETE, ADMIN_UPDATE)
                                .requestMatchers(GET,"/materials/*/detail").hasAnyRole(ADMIN,USER)
                                .requestMatchers(PUT,"/materials/*/update").hasAnyAuthority(ADMIN_DELETE, ADMIN_UPDATE)
                                .requestMatchers(GET,"/suppliers/all").hasAnyRole(ADMIN,USER)
                                .requestMatchers(POST,"/suppliers/save").hasAnyAuthority(ADMIN_CREATE)
                                .requestMatchers(GET,"/suppliers/*/detail").hasAnyRole(ADMIN,USER)
                                .requestMatchers(PUT,"/suppliers/*/update").hasAnyAuthority(ADMIN_DELETE, ADMIN_UPDATE)
                                .requestMatchers(DELETE,"/suppliers/*/delete").hasAnyAuthority(ADMIN_DELETE, ADMIN_UPDATE)
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .build();

    }
}
