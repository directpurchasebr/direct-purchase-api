package br.com.directpurchase.auth.service;

import java.util.Arrays;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.dto.UsuarioDto;
import br.com.directpurchase.dao.UsuarioDao;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.response.LoginResponse;
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

	public LoginResponse logar(final String login, final String senha) throws ValidationException {

		final String senhaEnc = PasswordUtil.encryptPassword(senha);
		UsuarioDto retorno = usuarioDao.findUsuarioByLoginSenha(login, senhaEnc);

		if (retorno != null) {
			String token = generateToken(retorno.getLogin());

			return LoginResponse.builder().accessToken(token).nome(retorno.getNome()).email(retorno.getEmail())
					.login(retorno.getLogin()).roles(Arrays.asList(retorno.getPerfilId().toString())).build();
		} else {
			throw new ValidationException("Usuario ou senha nao encontrado");
		}
	}

	public String generateToken(String field) {
		SecretKey key = new SecretKeySpec(secret.getBytes(), Jwts.SIG.HS512.key().build().getAlgorithm());
		return Jwts.builder().subject(field).expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(key.getEncoded())).compact();
	}

}
