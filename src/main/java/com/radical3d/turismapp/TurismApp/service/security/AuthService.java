package com.radical3d.turismapp.TurismApp.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.controller.security.AuthResponse;
import com.radical3d.turismapp.TurismApp.controller.security.LoginRequest;
import com.radical3d.turismapp.TurismApp.controller.security.RegisterRequest;
import com.radical3d.turismapp.TurismApp.model.security.Role;
import com.radical3d.turismapp.TurismApp.model.security.User;
import com.radical3d.turismapp.TurismApp.model.security.UserAdmin;
import com.radical3d.turismapp.TurismApp.repository.security.IUserAdminRepository;
import com.radical3d.turismapp.TurismApp.repository.security.IUserRepository;
import com.radical3d.turismapp.TurismApp.security.jwt.JWTUtils;
import com.radical3d.turismapp.TurismApp.utils.AppUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {
        @Autowired
        private IUserRepository userRepository;

        @Autowired
        private IUserAdminRepository userAdminRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private AuthenticationManager authManager;

        @Autowired
        private JWTUtils jwtUtils;

        @Value("${application.jwt-cookie-name}")
        private String JWT_COOKIE_NAME;

        @Value("${application.rtoken-cookie-name}")
        private String JWT_REFRESH_COOKIE_NAME;

        public AuthResponse registerUser(RegisterRequest registerRequest) {
                User user = userRepository.findByUsername(registerRequest.getUsername())
                                .orElse(null);
                if (user != null)
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User already exists in records!");

                user = User.builder()
                                .username(registerRequest.getUsername())
                                .enabled(registerRequest.isEnabled())
                                .password(passwordEncoder.encode(registerRequest.getPassword()))
                                .role(registerRequest.getUserType() == 'C' ? Role.ROLE_CITY : Role.ROLE_BUSSINESS)
                                .build();

                user = userRepository.save(user);

                UserAdmin userAdmin = UserAdmin.builder()
                                .user(user)
                                .name(registerRequest.getName())
                                .mail(registerRequest.getMail())
                                .position(registerRequest.getPosition())
                                .userType(registerRequest.getUserType())
                                .build();
                userAdmin = userAdminRepository.save(userAdmin);

                return AuthResponse.builder()
                                .username(user.getUsername())
                                .userType(userAdmin.getUserType())
                                .message("User Created Succesfully. Please log into the platform to start!")
                                .build();
        }

        public AuthResponse login(HttpServletResponse response, LoginRequest body) {
                try {
                        authManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        body.getUsername(),
                                                        body.getPassword()));
                } catch (AuthenticationException e) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
                }

                String token = jwtUtils.generateToken(body.getUsername());
                String refreshToken = jwtUtils.generateRefreshToken(body.getUsername());

                setJwtCookies(response, token, refreshToken, false);

                return AuthResponse.builder()
                                .username(body.getUsername())
                                .userType(
                                                userAdminRepository.getuserTypeByUsername(body.getUsername()).get())
                                .build();
        }

        private void setJwtCookies(HttpServletResponse response, String token, String refreshToken, boolean logout) {
                if (!logout && (token == null || refreshToken == null))
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Token generation failed");

                ResponseCookie jwtCookie = ResponseCookie.from(JWT_COOKIE_NAME, token)
                                .httpOnly(true)
                                .path("/")
                                .secure(true)
                                .sameSite(SameSite.NONE.attributeValue())
                                .maxAge(logout ? 0 : jwtUtils.jwtConfig.LIFETIME_TOKEN_SECONDS.longValue())
                                .build();

                ResponseCookie refreshJwtCookie = ResponseCookie.from(JWT_REFRESH_COOKIE_NAME, refreshToken)
                                .httpOnly(true)
                                .path("/")
                                .secure(true)
                                .sameSite(SameSite.NONE.attributeValue())
                                .maxAge(logout ? 0 : jwtUtils.jwtConfig.LIFETIME_REFRESH_TOKEN_SECONDS.longValue())
                                .build();

                response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
                response.addHeader(HttpHeaders.SET_COOKIE, refreshJwtCookie.toString());
        }

        public AuthResponse getLoggeduserData() {
                UserDetails userDetails = (UserDetails) AppUtils.getContextAuthentication().getPrincipal();
                
                return AuthResponse.builder()
                                .username(userDetails.getUsername())
                                .userType(userAdminRepository.getuserTypeByUsername(userDetails.getUsername()).get())
                                .message("User logged in")
                                .build();
        }

        public AuthResponse logout(HttpServletResponse response) {
                setJwtCookies(response, "", "", true);
                return AuthResponse.builder()
                                .message("User logged out")
                                .build();
        }

        public AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
                String refreshToken = AppUtils.getRequestCookieValue(request, JWT_REFRESH_COOKIE_NAME);
                String username = jwtUtils.getUsernameFromToken(refreshToken);
                UserDetails userDetails = userRepository.findByUsername(username)
                                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token is invalid"));
                if(!jwtUtils.isTokenValid(refreshToken, userDetails))
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token is invalid");
                
                String token = jwtUtils.generateToken(username);
                        refreshToken = jwtUtils.generateRefreshToken(username);

                setJwtCookies(response, token, refreshToken, false);

                return AuthResponse.builder()
                                        .message("Tokens generated succesuffly")
                                        .username(username)
                                        .userType(userAdminRepository.getuserTypeByUsername(username).get())
                                        .build();
        }

}
