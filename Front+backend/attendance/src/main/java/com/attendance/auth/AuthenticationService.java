package com.attendance.auth;

import com.attendance.token.Token;
import com.attendance.token.TokenRepository;
import com.attendance.token.TokenType;
import com.attendance.config.JwtService;
import com.attendance.user.User;
import com.attendance.user.UserRepository;
import com.attendance.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * The AuthenticationService class provides authentication related functionality.
 * It allows users to register, login, and refresh tokens.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final HttpServletResponse response;

    /**
     * Registers a new user.
     *
     * @param request The RegisterRequest object containing the user's registration details.
     * @return The AuthenticationResponse object containing the access token and refresh token.
     */
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode((request.getPassword())))
                .role(request.getRole())
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken, TokenType.BEARER);
        saveUserToken(savedUser, refreshToken, TokenType.REFRESH);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    /**
     * Authenticates a user and generates an access token and refresh token.
     *
     * @param request The AuthenticationRequest object containing the user's login details.
     * @return The AuthenticationResponse object containing the access token and refresh token.
     */
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);
        // Creates a secure cookie and sets in the response header
//        String cookieValue = String.format("refreshToken=%s; Path=/; HttpOnly; SameSite=None; Max-Age=86400", refreshToken);
//        response.setHeader("Set-Cookie", cookieValue);


        revokeAllUserTokens(user);
        saveUserToken(user, refreshToken, TokenType.REFRESH);
        saveUserToken(user, jwtToken, TokenType.BEARER);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    /**
     * Saves a user token in the database.
     *
     * @param user      The User object for which the token is associated.
     * @param token     The token value.
     * @param tokenType The type of the token (REFRESH or BEARER).
     */
    private void saveUserToken(User user, String token, TokenType tokenType) {
        var tokenEntity = Token.builder()
                .user(user)
                .token(token)
                .tokenType(tokenType)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(tokenEntity);
    }

    /**
     * Revokes only BEARER tokens associated with a user.
     *
     * @param user The User object for which the tokens need to be revoked.
     */
    private void revokeBearerTokensUser(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;

        validUserTokens.stream()
                .filter(token -> token.getTokenType() == TokenType.BEARER)
                .forEach(token -> {
                    token.setExpired(true);
                    token.setRevoked(true);
                });
        tokenRepository.saveAll(validUserTokens);
    }
    /**
     * Revokes all tokens associated with a user.
     *
     * @param user The User object for which the tokens need to be revoked.
     */
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWTRefreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Refresh token is missing");
            return;
        }
        final String username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.userRepository.findByUsername(username).orElseThrow(() -> new IOException("User not found"));
            // Compare token in cookie with the one saved in the database
            var savedToken = userService.getRefreshToken(user);
            if (!refreshToken.equals(savedToken)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid refresh token");
                return;
            }
            revokeBearerTokensUser(user);
            if (jwtService.isRevoked(refreshToken)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Refresh token is revoked");
                return;
            }
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                saveUserToken(user, accessToken, TokenType.BEARER);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Refresh token is invalid or expired");
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }
}
