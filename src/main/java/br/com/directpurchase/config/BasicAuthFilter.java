package br.com.directpurchase.config;

import java.io.IOException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.directpurchase.auth.CustomAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BasicAuthFilter extends OncePerRequestFilter {

	Logger log = LoggerFactory.getLogger(this.getClass());
	private static final int BASIC_LENGTH = 6;
	
	final CustomAuthentication provider;

	public BasicAuthFilter(CustomAuthentication provider) {
		this.provider = provider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		log.debug("[--] Basic Auth Filter");


        String headerAuthorization = request.getHeader("Authorization");

        if (headerAuthorization == null || !headerAuthorization.startsWith("Basic ")) {
        	log.debug("[--] Header inválido");
        	
            filterChain.doFilter(request, response);
            return;
        }

        String basicToken = headerAuthorization.substring(BASIC_LENGTH);
        byte[] basicTokenDecoded = Base64.getDecoder().decode(basicToken);
        String basicTokenValue = new String(basicTokenDecoded);
        String[] basicAuthsSplit = basicTokenValue.split(":");
        
		Authentication verified = provider.authenticate(basicAuthsSplit[0], basicAuthsSplit[1]);

		if (verified != null) {
			SecurityContextHolder.getContext().setAuthentication(verified);
		}
		
		filterChain.doFilter(request, response);
	}
}
