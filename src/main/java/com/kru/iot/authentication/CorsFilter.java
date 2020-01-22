package com.kru.iot.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Slf4j
public class CorsFilter implements Filter {

	@Value("${security.allowedOrigin:http://localhost}")
	private String allowedOrigin;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

		boolean isKnownOrigin = false;

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			log.debug("request from {}", request.getServerName());
			if (req.getServerName().equals("localhost") || (req.getServerName().contains(allowedOrigin))) {
				isKnownOrigin = true;
				response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
				if (isKnownOrigin) {
					response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, OPTIONS, DELETE, PUT");
	                response.setHeader("Access-Control-Allow-Credentials", "true");
	                response.setHeader("Access-Control-Max-Age", "3600");
	                response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, support-key");
	                response.setStatus(HttpServletResponse.SC_OK);
			    } else {
				    log.warn("CORS request blocked from unknown origin {}", req.getServerName());
				    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			    }
	          }
			
		  } else {
			    chain.doFilter(req, res);
		  }
	}



	@Override
	public void destroy() {
		
	}

}
