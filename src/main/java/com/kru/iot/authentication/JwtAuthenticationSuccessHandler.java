package com.kru.iot.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* <h1>Qualibrate 3.0 Cloud Platform API</h1>
* 
* <p>
* Define post-successful authentication steps.
* Nothing to do as we are not serving pages but only REST
*
* @author <a href="mailto:krunal.sabnis@qualibrate.com">Krunal Sabnis</a>
* @version 1.0
* @since   2018-06-01 
*/
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Do  anything specific here
    }

}
