package com.kru.iot.authentication;

import com.kru.iot.exceptions.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author kru on 22-1-20
 * @project events-query-service
 */
@Component
@Profile({"!test"})
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);
    @Autowired
    private JWTVerify jwtVerify;

    public JwtAuthenticationProvider() {
    }

    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthException {
    }

    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)authentication;
        String token = jwtAuthenticationToken.getToken();
        AuthenticatedUser authenticatedUser = null;

        try {
            authenticatedUser = this.jwtVerify.validate(token);
            return authenticatedUser;
        } catch (AuthException e) {
            log.error("error while auth {}", e.getMessage());
            throw new AuthException("error while verify the token");
        }
    }
}
