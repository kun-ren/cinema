package com.movie.api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

public final class JwtTokenUtil {

    //HTTP header name
    public final static String TOKEN_HEADER = "Authorization";

    //Expires after one week
    public final static long REMEMBER_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

    //Expires after one day
    public final static long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // Application secret
    private static final String APP_SECRET = "movie.api";

    // Role claims
    private static final String ROLE_CLAIMS = "roles";

    /**
     * Create a token
     */
    public static String createToken(String username, List<String> roles, long expiration) {
        Map<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, roles);
        return Jwts.builder()
                .setSubject(username)
                .setClaims(map)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    /**
     * Read the token claims
     */
    private static Claims getTokenClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(APP_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch ( ExpiredJwtException e ) {
            claims = e.getClaims();
        }
        return claims;
    }

    /**
     * Read the username from the token
     */
    public static String getUsername(String token) {
        return getTokenClaims(token).getSubject();
    }

    /**
     * Read user roles from the token
     */
    public static List<String> getTokenRoles(String token) {
        List<String> roles = new ArrayList<>();
        Object object = getTokenClaims(token).get(ROLE_CLAIMS);
        if (object instanceof ArrayList<?>) {
            for (Object o : (List<?>) object) {
                roles.add((String) o);
            }
        }
        return roles;
    }

    /**
     * Check whether the token has expired
     */
    public static boolean isExpiration(String token) {
        return getTokenClaims(token).getExpiration().before(new Date());
    }

}
