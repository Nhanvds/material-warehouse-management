package com.demo.mwm.config;

import com.demo.mwm.utils.AuthoritiesConstants;
import com.demo.mwm.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@Scope(value = "singleton")
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger();
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }


    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        final String authHeader = request.getHeader(AuthoritiesConstants.HEADER_AUTHORIZATION);
        if (Objects.isNull(authHeader) || !authHeader.startsWith(AuthoritiesConstants.TOKEN_PREFIX_BEARER_)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(AuthoritiesConstants.STARTING_POSITION_TOKEN);
        String username = jwtUtils.extractUsername(token);
        if (Objects.nonNull(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        logger.info("pass jwt");
        filterChain.doFilter(request, response);
    }

    /**
     * Determines whether the filter should not be applied to the given request.
     *
     * @param request the HttpServletRequest object.
     * @return true if the filter should not be applied to the request, false otherwise.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        for (String path : AuthoritiesConstants.WHITE_LISTED) {
            if (request.getServletPath().contains(path)) {
                return true;
            }
        }
        return false;
    }
}
