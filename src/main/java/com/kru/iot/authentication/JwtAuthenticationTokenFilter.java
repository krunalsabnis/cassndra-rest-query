package com.kru.iot.authentication;

import com.kru.iot.exceptions.AuthException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${jwt.header:Authorization}")
    private String tokenHeader;

    public JwtAuthenticationTokenFilter() {
        super("/api/**");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(this.tokenHeader);

        if (header == null || !header.startsWith("Bearer ")) {
        	response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new AuthException("No authentication token found in request headers");
        }

        String authToken = header.substring(7);
        Authentication auth = null;
        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);
       	auth = getAuthenticationManager().authenticate(authRequest);

        return auth;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, org.springframework.security.core.AuthenticationException failed)
			throws IOException, ServletException {
    	super.unsuccessfulAuthentication(request, response, failed);

		SecurityContextHolder.clearContext();
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }
    /**
     * Make sure the rest of the filterchain is satisfied
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(request, response);
    }
}