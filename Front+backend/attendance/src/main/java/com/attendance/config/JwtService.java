package com.attendance.config;

import com.attendance.token.Token;
import com.attendance.token.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.SignatureException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    private final TokenRepository tokenRepository;
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    /**
     * Extracts all claims from a JWT token.
     *
     * @param token The JWT token from which to extract the claims.
     * @return The payload claims if the token is valid; otherwise, null.
     */
    private Claims extractAllClaims(String token){
        try {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature: " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
    public String generateToken(UserDetails userDetails)
    {
        return buildToken(new HashMap<>(),userDetails, jwtExpiration);
    }
    public String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration)
    {
        return Jwts.builder().claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +expiration))
                .signWith(getSignInKey())
                .compact();
    }
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }
    /**
     * Checks if a token is valid for a given user details.
     *
     * @param token         The token to be validated.
     * @param userDetails  The user details associated with the token.
     * @return              {@code true} if the token is valid for the user details, {@code false} otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
    public boolean isRevoked(String token) {
        Optional<Token> storedToken =  tokenRepository.findByToken(token);
        return storedToken.isPresent() && storedToken.get().isRevoked();
    }
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
