package com.hms.gateway.filter;



import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config>{

    private static final String SECRETT_KEY = "67a98fb2ad0f13ddea88a777d250e382c285dc8e37d0fdfc0f63f3cc147736605d0eb241d0a692b614448e3dbb1eb757f4f2881475f6558c12687684c34183e1";

    public TokenFilter(){
        super(Config.class);
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRETT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange,chain) ->{
            String path = exchange.getRequest().getPath().toString();
            if(path.equals("/user/login")||path.equals("/user/register")){
                return chain.filter(exchange.mutate().request(r->r.header("X-Secret-Key", "SECRET")).build());
            }
            
            HttpHeaders header = exchange.getRequest().getHeaders();
            if(!header.containsKey(HttpHeaders.AUTHORIZATION)){
                throw new RuntimeException("Authorization header is missing");
            }
            
            String authHeader = header.getFirst(HttpHeaders.AUTHORIZATION);
            if(authHeader == null || !authHeader.startsWith("Bearer")){
                throw new RuntimeException("Authorization header is invalid");
            }

            String token = authHeader.substring(7);
            try{
                Claims claims = Jwts.parser()                       // ✅ use parser(), not parserBuilder()
                                    .verifyWith(getSignKey())          // ✅ new method
                                    .build()
                                    .parseSignedClaims(token)          // ✅ replaces parseClaimsJws
                                    .getPayload();  
                                exchange = exchange.mutate().request(r->r.header("X-Secret-Key", "SECRET")).build();
                   
            }catch(Exception e){
                throw new RuntimeException("token is Invalid");
            }
            return chain.filter(exchange);
        };
    }

    public static class Config{

    }
    
}
