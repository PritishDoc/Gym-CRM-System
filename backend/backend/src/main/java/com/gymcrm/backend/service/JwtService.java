package com.gymcrm.backend.service;

import com.gymcrm.backend.repository.TokenBlacklistRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    // keep your base64 secret here or move to application.properties and @Value-inject it
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    private final TokenBlacklistRepository tokenBlacklistRepository; // injected

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        final Date now = new Date();
        final Date expiry = new Date(now.getTime() + 1000L * 60 * 60 * 24); // 24 hours

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())   // subject = username (email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generic claim extractor
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claimsResolver.apply(claims);
        } catch (JwtException ex) {
            // invalid token
            return null;
        }
    }

    // Subject (username / email)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Expiration
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpiredInternal(String token) {
        Date exp = extractExpiration(token);
        if (exp == null) return true; // treat invalid/parse-failure as expired/invalid
        return exp.before(new Date());
    }

    /**
     * Checks token validity against given UserDetails AND blacklist.
     * Returns false if token is invalid, expired, or blacklisted.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        if (token == null || userDetails == null) return false;

        // parse subject
        final String username = extractUsername(token);
        if (username == null) return false;

        // check username equality (case-insensitive for safety)
        boolean usernameMatches = username.equalsIgnoreCase(userDetails.getUsername());

        if (!usernameMatches) return false;

        // check expiration
        if (isTokenExpiredInternal(token)) return false;

        // check blacklist safely (repository may be null if not injected correctly)
        try {
            if (tokenBlacklistRepository != null && tokenBlacklistRepository.existsByToken(token)) {
                return false;
            }
        } catch (Exception ex) {
            // If blacklist check fails for any reason, fail-safe: treat token as invalid
            return false;
        }

        return true;
    }

    // Optional convenience: validate token without userDetails (e.g., for logout/blacklist checks)
    public boolean isTokenWellFormedAndNotExpired(String token) {
        if (token == null) return false;
        return !isTokenExpiredInternal(token);
    }
}
