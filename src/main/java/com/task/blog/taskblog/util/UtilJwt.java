package com.task.blog.taskblog.util;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.blog.taskblog.vo.TokenVo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class UtilJwt {

    @Autowired
    ObjectMapper objectMapper;


    @Value("${" + "jwt.secret" + ":#{null}}")
    private String jwtSecret;
    @Value("${" + "jwt.tokenType" + ":#{null}}")
    private String jwtTokenType;

    public TokenVo generateToken(String username) {

        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + (600000));
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts
            .builder()
            .setId(UUID.randomUUID().toString())
            .setSubject(username)
            .claim("authorities", 
            grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();

        return new TokenVo(token, username, issuedAt, expiration);
    }

    private Key getSignKey() {
        byte[] keys = jwtSecret.getBytes();

        return Keys.hmacShaKeyFor(keys);
    }

    public Claims parseAccessToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(token).getBody();
    }

    public String getAccessToken(String authorizationString) {
        if (authorizationString != null && authorizationString.startsWith("")) {
            return authorizationString.replaceFirst(jwtTokenType + " ", "");
        }

        return null;
    }

    public String getAccessToken(HttpServletRequest request) {
        String authenticationString = request.getHeader("Authorization");

        return this.getAccessToken(authenticationString);
    }

    
    public String getTokenType() {
        return jwtTokenType;
    }



    
}
