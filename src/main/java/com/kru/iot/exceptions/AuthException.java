package com.kru.iot.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author kru on 22-1-20
 * @project events-query-service
 */
public class AuthException extends AuthenticationException {
    private static final long serialVersionUID = -999147300466204514L;

    public AuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthException(String msg) {
        super(msg);
    }
}
