package br.com.directpurchase.auth.service;

import java.util.Arrays;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.dao.AuthDao;
import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.entity.UsuarioSession;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.request.LoginRequest;
import br.com.directpurchase.response.LoginResponse;
import br.com.directpurchase.transform.UsuarioTransform;
import br.com.directpurchase.util.PasswordUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	@Autowired
	private AuthDao authDao;

	@Autowired
	private UsuarioTransform usuarioTransform;

	public LoginResponse logar(LoginRequest request) throws ValidationException {

		final String senhaEnc = PasswordUtil.encryptPassword(request.getSenha());
		UsuarioPayload usuarioLogado = authDao.findUsuarioByLoginSenha(request.getUsuario(), senhaEnc);
		if (usuarioLogado != null) {
			UsuarioPayload fetchUser = usuarioTransform.fetchUsuarioPayload(usuarioLogado.getUsuarioId());
			if (fetchUser == null) {
				throw new ValidationException("Usuario nao encontrado");
			}

			String tokenAccess = generateTokenAcess(fetchUser);
			if (fetchUser.getSession() != null && fetchUser.getSession().getIndSession()) {
				throw new ValidationException("Sessao ja iniciada!!!!");
			} else {
				if (request.getDeviceId() != null && request.getDeviceInfo() != null) {
					String tokenUser = generateTokenUser(
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

	public void intaivaSessaoUsuario(String token) {
		authDao.intaivaSessaoUsuario(token);
	}

	public String generateTokenUser(String deviceId, String userAgent, String platform, String language) {
		SecretKey key = new SecretKeySpec(secret.getBytes(), Jwts.SIG.HS512.key().build().getAlgorithm());
		return Jwts.builder()
				.subject(deviceId)
				.claim("userAgent", userAgent)
				.claim("platform", platform)
				.claim("language", language)
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(key.getEncoded()))
				.compact();
	}

	public String generateTokenAcess(UsuarioPayload usuario) {
		SecretKey key = new SecretKeySpec(secret.getBytes(), Jwts.SIG.HS512.key().build().getAlgorithm());
		return Jwts.builder()
				.subject(usuario.getLogin())
				.claim("usuarioId", usuario.getUsuarioId())
				.claim("nome", usuario.getNome())
				.claim("email", usuario.getEmail())
				.claim("perfil", usuario.getPerfil())
				.claim("indEstoque", usuario.getIndEstoque())
				.claim("status", usuario.getStatus())
				.claim("fornecedores", usuario.getFornecedores())
				.claim("compradores", usuario.getCompradores())
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(key.getEncoded()))
				.compact();
	}

}
