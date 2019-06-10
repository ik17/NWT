package com.example.demo;

import java.io.IOException;
import java.security.SignatureException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.ExpiredJwtException;



public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	static final String SECRET = "DevDojoFoda";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/users/sign-up";
    static final long EXPIRATION_TIME = 86400000L;
    
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse 
                 res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        String role = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
        	System.out.println(header.toString());
            authToken = header.replace(TOKEN_PREFIX,"");
            System.out.println("Here boi");
            System.out.println(authToken);
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
                role = jwtTokenUtil.getRoleFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("An error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("Token is expired and not valid anymore", e);
            }
        } else {
            logger.warn("Couldn't find bearer string, will ignore the header");
        }
        if (username != null && 
                 SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = 
                          userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new 
                     UsernamePasswordAuthenticationToken(userDetails, null, null);
                authentication.setDetails(new 
                     WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("Authenticated user " + username + ", setting security context");                          
                SecurityContextHolder.getContext().setAuthentication(
                          authentication);
            }
        }
        req.setAttribute("Rola", role);
        /*res.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");*/
        chain.doFilter(req, res);
    }
}