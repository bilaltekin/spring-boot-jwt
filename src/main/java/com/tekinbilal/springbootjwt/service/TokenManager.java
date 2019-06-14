package com.tekinbilal.springbootjwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenManager {

    private  String secretKey="secretKey";
    private static final int validity=5*60*1000;
    //public static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken( String username){


         Map<String,Object> claims = new HashMap<>();
      return   Jwts.builder().setClaims(claims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10 ))
                .signWith(SignatureAlgorithm.HS256,secretKey).compact();


    }

    public boolean tokenValidate(String token){

        if (getUserNameToken(token) != null && isExpired(token))
            return true;

        return false;

    }

    private boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));

    }


    public String getUserNameToken(String token){

        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
