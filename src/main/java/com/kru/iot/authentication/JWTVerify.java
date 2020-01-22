package com.kru.iot.authentication;

import com.kru.iot.exceptions.AuthException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author kru on 22-1-20
 * @project events-query-service
 */
@Component
@NoArgsConstructor
public class JWTVerify {

    public AuthenticatedUser validate(String token) {

        /**
         * This is a very simple validate method
         * Ideally here token should be verified for its claims
         * and accordingly AuthenticatedUser should be build and returned.
         *
         */
        if (token.equals("1234"))
            return AuthenticatedUser.builder()
                    .email("krunalsabnis@gmail.com")
                    .userId("krunalsabnis@gmail.com")
                    .userName("K S").build();
        else
            throw new AuthException("invalid tokem");

    }


}
