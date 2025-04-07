package br.com.directpurchase.auth.service;

import java.util.Arrays;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.dao.UsuarioDao;
import br.com.directpurchase.exception.ValidationException;
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
	private UsuarioDao usuarioDao;

	@Autowired
	private UsuarioTransform usuarioTransform;

	public LoginResponse logar(final String login, final String senha) throws ValidationException {

		final String senhaEnc = PasswordUtil.encryptPassword(senha);
		UsuarioPayload usuarioLogado = usuarioDao.findUsuarioByLoginSenha(login, senhaEnc);
		if (usuarioLogado != null) {
			UsuarioPayload transform = usuarioTransform.fetchUsuarioPayload(usuarioLogado.getUsuarioId());
			String token = generateToken(transform);

			return LoginResponse.builder()
			        .accessToken(token)
			        .nome(transform.getNome())
			        .email(transform.getEmail())
			        .login(transform.getLogin())
			        .roles(Arrays.asList(transform.getPerfilId().toString()))
			        .build();
		} else {
			throw new ValidationException("Usuario ou senha nao encontrado");
		}
	}

	public String generateToken(UsuarioPayload usuario) {
		SecretKey key = new SecretKeySpec(secret.getBytes(), Jwts.SIG.HS512.key().build().getAlgorithm());
		return Jwts.builder()
		        .subject(usuario.getLogin())
		        .claim("usuarioId", usuario.getUsuarioId())
		        .claim("nome", usuario.getNome())
		        .claim("email", usuario.getEmail())
		        .claim("perfilId", usuario.getPerfilId())
		        .claim("indEstoque", usuario.getIndEstoque())
		        .claim("status", usuario.getStatus())
		        .claim("fornecedores", usuario.getFornecedores())
		        .claim("compradores", usuario.getCompradores())
		        .expiration(new Date(System.currentTimeMillis() + expiration))
		        .signWith(Keys.hmacShaKeyFor(key.getEncoded()))
		        .compact();
	}

}
