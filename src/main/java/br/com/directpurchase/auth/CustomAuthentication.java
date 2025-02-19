package br.com.directpurchase.auth;

import org.springframework.security.core.Authentication;

public interface CustomAuthentication {

	public Authentication authenticate(String username, String password);
	
}
