package com.example.demo;







import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
@Component
public class PreFilter extends ZuulFilter {
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
    private static Logger log = LoggerFactory.getLogger(PreFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 2;
    }
    @Override
    public boolean shouldFilter() {
        return true;
    }
    
    @Override
    public Object run() {
    	System.out.println("Usao u jebeni filter");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();    
         // Add a custom header in the request
        String s = request.getHeader("Authorization").substring(7);
        System.out.println(s);
        System.out.println(jwtTokenUtil == null);
        String role = jwtTokenUtil.getRoleFromToken(s);
        System.out.println(role);
        ctx.addZuulRequestHeader("role",
                 role);
       // ctx.put("role", request.getHeader(s));
        log.info(String.format("%s request to %s", request.getMethod(), 
                 request.getRequestURL().toString()));
        return null;
    }
}