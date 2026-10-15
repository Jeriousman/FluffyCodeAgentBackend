package com.hojui.AgentIo.filter;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hojui.AgentIo.service.JwtService;
import com.hojui.AgentIo.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtFilter
 */
// spting security filter에 넣어줄 jwtfilter 만들기
@Component 
public class JwtFilter extends OncePerRequestFilter {


    private JwtService jwtService;
    private ApplicationContext applicationContext;
    

    public JwtFilter(JwtService jwtService, ApplicationContext applicationContext) {
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeader != null &&  authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7); //Skip Bearer part
            username = jwtService.extractUserName(token);
        }


        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){ //username이 존재하면서 이미 인증허가가 되지 않은 경우에만, 
            UserDetails userDetails = applicationContext.getBean(UserService.class).loadUserByUsername(username); // getBean(UserService.class) 은 UserService의 객체를 돌려주기 때문에 loadUserByUsername을 바로 . 으로 이어서 사용가능 

            if (jwtService.validateToken(token, userDetails)){  // JWT token 

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
    filterChain.doFilter(request, response);

    }
}

