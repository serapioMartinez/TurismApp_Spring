package com.radical3d.turismapp.TurismApp.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.configuration.AppHeadersBean;
import com.radical3d.turismapp.TurismApp.repository.security.IUserRepository;
import com.radical3d.turismapp.TurismApp.security.jwt.JWTUtils;
import com.radical3d.turismapp.TurismApp.utils.AppUtils;
import com.radical3d.turismapp.TurismApp.utils.LoggerHelper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private AppHeadersBean appHeaders;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private IUserRepository userRepository;

    private final String[] NO_FILTER_PATHS = {
            "/api/v1/auth/.*"
    };

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        LoggerHelper.info("Validating JWT should filter path: " + path);
        for (String no_filter : NO_FILTER_PATHS) {
            if (path.matches(no_filter))
                return true;
        }
        LoggerHelper.info("Filtering");
        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = AppUtils.getRequestCookieValue(request, appHeaders.getJWT_COOKIE_NAME());

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
            String username = jwtUtils.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userRepository.findByUsername(username)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT Token Invalid"));
                if (jwtUtils.isTokenValid(token, userDetails)) {
                    SecurityContextHolder.getContext()
                            .setAuthentication(jwtUtils.getAuthentication(userDetails));
                }
            }
        
        filterChain.doFilter(request, response);
    }

}
