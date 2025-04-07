package br.com.directpurchase.auth.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.directpurchase.auth.payload.UsuarioPayload;

@Component
public class AuthUtils {

	public UsuarioPayload getUsuarioLogado() {
		return (UsuarioPayload) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
