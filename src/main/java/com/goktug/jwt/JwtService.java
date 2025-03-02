package com.goktug.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    private static final String SECRET_KEY = "1a06fda55bb8f56d5fb2483ec00e318474ce2dfad5faa710242640fcc0b88c2a9c562882072d5df2415198d9a9fb2085b3666e655f6b19579d0a8ffcde680475c60f1cd6116738561a0a3a3b255b66417ad35bb4a10d572f2afeaf2eec879b0ef3c530106463edd21f73400d4150389f0b7e4e3e615777589190eb4f8187bace19d6e58baaa49eddc3b0c200dc811c593e191288ca3314a9b39a5f02c87fdc7b51c417d6e02f0477d92d3b29bbb353ecca15c018b72e34d61633cc8a9225231d7d72b375700004cf58d2f451a1f22824602fd1488c663159af86ce80501c30b6d44c89494d8e48b2ca7846c7a814dc60e287d88cfca46365094e6b91dca82683";

    //! Token oluşturma
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setIssuedAt(new Date()).setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }

    //! Key şifre oluşturma aslında
    public Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    //! Çözülmüş token dan istenen değerleri alma
    public <T> T exportToken(String token,Function<Claims ,T> claimsFunction){
        Claims claims = getClaims(token);
        return claimsFunction.apply(claims);
    } 

    //! Username çekme token dan
    public String getUsernameByToken(String token){
        return exportToken(token,Claims::getSubject);
    } 

    //! Token hala geçerlimi
    public boolean isTokenValid(String token) {
        Date expiredDate = exportToken(token,Claims::getExpiration);
        return new Date().before(expiredDate); 
    }   

    //! Token çözme
    public Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
        return claims;
    }

}
