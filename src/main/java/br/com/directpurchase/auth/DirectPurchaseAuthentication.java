package br.com.directpurchase.auth;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import br.com.directpurchase.dto.UsuarioDto;
import br.com.directpurchase.service.LoginService;

@Component(value = "directPurchaseAuthentication")
public class DirectPurchaseAuthentication implements AuthenticationProvider, CustomAuthentication {

	@Autowired
	private LoginService loginService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		return authenticate(username, password);
	}

	@Override
	public Authentication authenticate(String username, String password) {

		UsuarioDto login = loginService.logar(username, password);
		if (login != null && login.getStatus().equals("SUCCESS")) {
			return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());

		} else {
			throw new BadCredentialsException("Usuário inválido.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}