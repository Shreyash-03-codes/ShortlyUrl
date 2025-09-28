package com.springboot.util;

import com.springboot.dto.auth.UserLoginRequestDto;
import com.springboot.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    private Key getSecretkey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id",user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*15))
                .signWith(getSecretkey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSecretkey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token,UserDetails user){
        String username=extractUsername(token);
        Date exp=Jwts
                .parserBuilder()
                .setSigningKey(getSecretkey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return username.equals(user.getUsername()) && exp.after(new Date());
    }


}
