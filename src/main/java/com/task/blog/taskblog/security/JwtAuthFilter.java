package com.task.blog.taskblog.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.task.blog.taskblog.util.UtilJwt;
import com.task.blog.taskblog.vo.UserAuthVo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    @Autowired
    private UtilJwt jwtUtil;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String token = jwtUtil.getAccessToken(request);
            if (token != null) {
                Claims claims = jwtUtil.parseAccessToken(token);
                if (claims.get("authorities") != null) {
                    setupSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            }else{
                SecurityContextHolder.clearContext();
            }
        } catch (ExpiredJwtException e) {
            SecurityContextHolder.clearContext();
        }catch (UnsupportedJwtException e) {
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
           SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
        
    }

    private void setupSpringAuthentication(Claims claims) {

        @SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get("authorities");
        UserAuthVo userInfo = new UserAuthVo(claims.getSubject());
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userInfo, null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

    }
    
}
