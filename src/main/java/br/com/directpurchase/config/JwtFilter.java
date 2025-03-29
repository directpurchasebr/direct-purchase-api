package br.com.directpurchase.config;

import java.io.IOException;
import java.util.ArrayList;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Value("${jwt.secret}")
	private String secret;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			SecretKey key = new SecretKeySpec(secret.getBytes(), Jwts.SIG.HS512.key().build().getAlgorithm());
			Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
			String username = claims.getSubject();
			SecurityContextHolder.getContext()
					.setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
		}
		filterChain.doFilter(request, response);
	}
}