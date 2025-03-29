package br.com.directpurchase.config;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	public String generateToken(String field) {
		
		SecretKey key = new SecretKeySpec(secret.getBytes(), Jwts.SIG.HS512.key().build().getAlgorithm());
		
		return Jwts.builder().subject(field).expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(key.getEncoded())).compact();
	}
}