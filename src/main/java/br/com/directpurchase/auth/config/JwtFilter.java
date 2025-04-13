package br.com.directpurchase.auth.config;

import java.io.IOException;
import java.util.ArrayList;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.service.AuthService;
import br.com.directpurchase.auth.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private AuthService authService;

	@Autowired
	private TokenUtils tokenUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			SecretKey key = new SecretKeySpec(secret.getBytes(), Jwts.SIG.HS512.key().build().getAlgorithm());
			try {
				Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
				UsuarioPayload usuario = tokenUtils.convertClaimstoUsuario(token, claims);

				SecurityContextHolder.getContext()
						.setAuthentication(new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>()));

			} catch (ExpiredJwtException e) {
				authService.intaivaSessaoUsuario(token);

				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Token expirado");
				return;
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Token inválido");
				return;
			}

		}
		filterChain.doFilter(request, response);
	}

}