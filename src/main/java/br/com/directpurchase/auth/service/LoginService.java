package br.com.directpurchase.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.dto.UsuarioDto;
import br.com.directpurchase.auth.response.LoginResponse;
import br.com.directpurchase.config.AuthService;
import br.com.directpurchase.dao.UsuarioDao;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.util.PasswordUtil;

@Service
public class LoginService {

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private AuthService authService;

	public LoginResponse logar(final String login, final String senha) throws ValidationException {

		final String senhaEnc = PasswordUtil.encryptPassword(senha);
		UsuarioDto retorno = usuarioDao.findByUsuarioLoginSenha(login, senhaEnc);

		if (retorno != null) {
			String token = authService.generateToken(retorno.getLogin());
			return LoginResponse.builder().token(token).build();
		} else {
			throw new ValidationException("Usuario ou senha nao encontrado");
		}

	}
}
