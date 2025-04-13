package br.com.directpurchase.auth.utils;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.payload.UsuarioSessionPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public UsuarioPayload convertClaimstoUsuario(String token, Claims claims) {
        String login = claims.getSubject();
        Integer usuarioId = claims.get("usuarioId", Integer.class);
        String nome = claims.get("nome", String.class);
        String email = claims.get("email", String.class);
        String perfil = claims.get("perfil", String.class);
        Boolean indEstoque = claims.get("indEstoque", Boolean.class);
        String status = claims.get("status", String.class);
        List fornecedores = claims.get("fornecedores", List.class);
        List compradores = claims.get("compradores", List.class);

        return UsuarioPayload.builder()
                .usuarioId(usuarioId)
                .nome(nome)
                .login(login)
                .email(email)
                .perfil(perfil)
                .indEstoque(indEstoque)
                .status(status)
                .fornecedores(fornecedores)
                .compradores(compradores)
                .session(UsuarioSessionPayload.builder().tokenAccess(token).build())
                .build();
    }
}
