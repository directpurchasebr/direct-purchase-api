package br.com.directpurchase.auth.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.dao.AuthDao;
import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.auth.utils.TokenUtils;
import br.com.directpurchase.entity.UsuarioSession;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.request.LoginRequest;
import br.com.directpurchase.response.LoginResponse;
import br.com.directpurchase.response.Status;
import br.com.directpurchase.transform.UsuarioTransform;
import br.com.directpurchase.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	private final AuthDao authDao;

	private final UsuarioTransform usuarioTransform;

	private final TokenUtils tokenUtils;

	private final AuthUtils authUtils;

	public AuthService(AuthDao authDao, UsuarioTransform usuarioTransform,
			AuthUtils authUtils, TokenUtils tokenUtils) {
		this.authDao = authDao;
		this.usuarioTransform = usuarioTransform;
		this.authUtils = authUtils;
		this.tokenUtils = tokenUtils;
	}

	public LoginResponse logar(LoginRequest request) throws ValidationException {

		final String senhaEnc = PasswordUtil.encryptPassword(request.getSenha());
		UsuarioPayload usuarioLogado = authDao.findUsuarioByLoginSenha(request.getUsuario(), senhaEnc);
		if (usuarioLogado != null) {
			UsuarioPayload fetchUser = usuarioTransform.fetchUsuarioPayload(usuarioLogado.getUsuarioId());
			if (fetchUser == null) {
				throw new ValidationException("Usuario nao encontrado");
			}

			String tokenAccess = tokenUtils.generateTokenAcess(fetchUser);
			if (fetchUser.getSession() != null && fetchUser.getSession().getIndSession()) {
				// throw new ValidationException("Sessao ja iniciada!!!!");

				log.info("Sessao ja iniciada para o token {}", fetchUser.getSession().getTokenAccess());
				log.info("device: {}", request.getDeviceInfo());
				log.info("Sessao ja logado: {}", fetchUser.getSession());

			} else {
				if (request.getDeviceId() != null && request.getDeviceInfo() != null) {
					String tokenUser = tokenUtils.generateTokenUser(
							request.getDeviceId(),
							request.getDeviceInfo().getUserAgent(),
							request.getDeviceInfo().getPlatform(),
							request.getDeviceInfo().getLanguage());

					UsuarioSession usuarioSession = usuarioTransform.transform(
							usuarioLogado.getUsuarioId(),
							request.getDeviceId(),
							request.getDeviceInfo().getUserAgent(),
							request.getDeviceInfo().getPlatform(),
							request.getDeviceInfo().getLanguage(),
							tokenUser, tokenAccess);
					authDao.salvar(usuarioSession);
				}
			}

			return LoginResponse.builder()
					.accessToken(tokenAccess)
					.nome(fetchUser.getNome())
					.email(fetchUser.getEmail())
					.login(fetchUser.getLogin())
					.roles(Arrays.asList(fetchUser.getPerfil().toString()))
					.build();
		} else {
			throw new ValidationException("Usuario ou senha nao encontrado");
		}
	}

	public Object logout() {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();
		intaivaSessaoUsuario(usuario.getSession().getTokenAccess());
		return new Status(true, "OK", "", usuario.getSession());
	}

	public Object validateToken() {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();
		return new Status(true, "OK", "", usuario.getSession());
	}

	public void intaivaSessaoUsuario(String token) {
		authDao.intaivaSessaoUsuario(token);
	}

}
