package br.com.directpurchase.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.directpurchase.auth.payload.UsuarioPayload;
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

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			SecretKey key = new SecretKeySpec(secret.getBytes(), Jwts.SIG.HS512.key().build().getAlgorithm());
			try {
				Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
				UsuarioPayload usuario = convertClaimstoUsuario(claims);

				SecurityContextHolder.getContext()
				        .setAuthentication(new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>()));

			} catch (ExpiredJwtException e) {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private UsuarioPayload convertClaimstoUsuario(Claims claims) {
		String login = claims.getSubject();
		Integer usuarioId = claims.get("usuarioId", Integer.class);
		String nome = claims.get("nome", String.class);
		String email = claims.get("email", String.class);
		Integer perfilId = claims.get("perfilId", Integer.class);
		Boolean indEstoque = claims.get("indEstoque", Boolean.class);
		String status = claims.get("status", String.class);
		List fornecedores = claims.get("fornecedores", List.class);
		List compradores = claims.get("compradores", List.class);

		return UsuarioPayload.builder()
		        .usuarioId(usuarioId)
		        .nome(nome)
		        .login(login)
		        .email(email)
		        .perfilId(perfilId)
		        .indEstoque(indEstoque)
		        .status(status)
		        .fornecedores(fornecedores)
		        .compradores(compradores)
		        .build();
	}

}