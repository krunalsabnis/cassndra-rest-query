package com.kru.iot.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author kru on 22-1-20
 * @project events-query-service
 */

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private static final long serialVersionUID = 1682365222054805420L;
    private String token;

    public JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return null;
    }

    public String getToken() {
        return this.token;
    }
}