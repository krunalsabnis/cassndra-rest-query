package com.kru.iot.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthenticationProvider authenticationProvider;

    public static final String PUBLIC_PATHS = "/api/v[0-9]+/auth/.*|.*swagger.*|/favicon\\.ico|/info|/health";

    public WebSecurityConfig() {
        super(true);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {

        return new ProviderManager(Arrays.asList(authenticationProvider));
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManager());
        authenticationTokenFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        return authenticationTokenFilter;
    }

    @Autowired
    private CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN)).and()
                //http
                // Allow anonymous access
                .anonymous().and()

                // Enable Basic Authentication
                .httpBasic().realmName("Cassandra REST APIs").and()

                .servletApi().and()

                // Stateless session management
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // Disable CSRF & Frame Options since we're not serving HTML content
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .headers().xssProtection().disable().and()


                .authorizeRequests()
                // Allow anonymous resource requests
                .antMatchers("/").permitAll()


                // Secure all APIs
                .regexMatchers(HttpMethod.OPTIONS, "/api/v[0-9]+/.*").permitAll();

        http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        http.headers().cacheControl();
        //.and()


    }

}

