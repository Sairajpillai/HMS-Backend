package com.hms.user.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final Long JWT_TOKEN_VALIDITY = 5 * 60 * 60L;
    private static final String SECRET_KEY = "67a98fb2ad0f13ddea88a777d250e382c285dc8e37d0fdfc0f63f3cc147736605d0eb241d0a692b614448e3dbb1eb757f4f2881475f6558c12687684c34183e1";

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        CustomUserDetails user = (CustomUserDetails) userDetails;
        claims.put("id",user.getId());
        claims.put("email",user.getEmail());
        claims.put("role",user.getRole());
        claims.put("name",user.getName());
        claims.put("profileId",user.getProfileId());
        return doGenerateToken(claims, user.getUsername());
    }

    // public String doGenerateToken(Map<String,Object> claims,String subject){
    // return Jwts.builder().setClaims(claims).setSubject(subject)
    // .issuedAt(new Date(System.currentTimeMillis())).
    // setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
    // .signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    // }

    private Key getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                // .signWith(getSignKey(), Jwts.SIG.HS512) // ✅ correct for 0.12.5
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }
}
