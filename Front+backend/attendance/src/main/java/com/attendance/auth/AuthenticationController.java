package com.attendance.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * AuthenticationController handles user authentication endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Registers a new user.
     *
     * @param request The RegisterRequest object containing the user's registration details.
     * @return The ResponseEntity containing the AuthenticationResponse object which includes the access token and refresh token.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Handles the login endpoint.
     *
     * @param request The AuthenticationRequest object containing the user's login details.
     *
     * @return The ResponseEntity object containing the AuthenticationResponse object, which includes the access token and refresh token.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse authResponse = service.login(request);
        String refreshToken = authResponse.getRefreshToken();
        ResponseCookie springCookie = ResponseCookie.from("JWTRefreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .domain("localhost")
                .sameSite("Strict")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, springCookie.toString());

        return ResponseEntity.ok().headers(headers).body(authResponse);
    }

    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param request  The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object for sending the response.
     * @throws IOException If an I/O error occurs while refreshing the token.
     */
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }



}
