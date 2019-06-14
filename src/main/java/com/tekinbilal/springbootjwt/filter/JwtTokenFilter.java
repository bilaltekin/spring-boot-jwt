package com.tekinbilal.springbootjwt.filter;

import com.tekinbilal.springbootjwt.service.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {


    private final TokenManager tokenManager;

    @Autowired
    public JwtTokenFilter(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        /**
         * Bearer Token
         */

        final String authHeader = httpServletRequest.getHeader("Authorization");

        String username = null;
        String token =null;

        if (authHeader != null && authHeader.contains("Bearer"))
        {
            token = authHeader.substring(7);

            try {
                username = tokenManager.getUserNameToken(token);
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
            }

            if (username !=null && token != null && SecurityContextHolder.getContext().getAuthentication()==null)
            {

                if (tokenManager.tokenValidate(token))
                {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                            new UsernamePasswordAuthenticationToken(username,null ,new ArrayList<>());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
