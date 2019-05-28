package com.example.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {   
	
	//quick fix
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 30000;
	@Value("${jwt.security.key}")
    private String jwtKey;
    private String doGenerateToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://jwtdemo.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +           
                          ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();
    }
   // Other methods
	public boolean validateToken(String authToken, UserDetails userDetails) {
		// TODO Auto-generated method stub
	Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtKey)).parseClaimsJws(authToken).getBody();
		return claims.getExpiration() != null && claims.containsKey("username") && claims.containsValue(userDetails.getUsername());
	}
	public String getUsernameFromToken(String authToken) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtKey)).parseClaimsJws(authToken).getBody();
		return claims.get("username", String.class);
	}
	public String getRoleFromToken(String authToken) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtKey)).parseClaimsJws(authToken).getBody();
		return claims.get("role", String.class);
	}
	public String generateToken(User user) {
		Object o = user.getUsername();
		Claims claims = Jwts.claims();
		claims.put("username", o);
		claims.put("password", user.getPassword());
		claims.put("role", user.getRole());
		//Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        //authorities.add(new SimpleGrantedAuthority(user.getRole()));
		//claims.put("scopes", authorities);
		/*List<String> lista =new ArrayList<String>();
		lista.add(user.getRole());*/
		
		//claims.put("authorities", lista);
		return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://jwtdemo.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +           
                          ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();
	}
}