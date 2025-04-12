package br.com.directpurchase.auth.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.entity.UsuarioSession;
import br.com.directpurchase.repository.UsuarioRepository;
import br.com.directpurchase.repository.UsuarioSessionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AuthDao {

	private final EntityManager roEM;
	private final UsuarioRepository usuarioRepository;
	private final UsuarioSessionRepository sessionRepository;

	public AuthDao(EntityManager roEM, UsuarioRepository usuarioRepository,
			UsuarioSessionRepository sessionRepository) {
		this.roEM = roEM;
		this.usuarioRepository = usuarioRepository;
		this.sessionRepository = sessionRepository;
	}

	@Transactional
	public void salvar(Usuario entity) {
		usuarioRepository.save(entity);
	}

	@Transactional
	public void salvar(UsuarioSession entity) {
		sessionRepository.save(entity);
	}

	public UsuarioPayload findUsuarioByLoginSenha(String login, String senha) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.usuario_id, ");
		sql.append("	p.descricao, ");
		sql.append("	u.nome, ");
		sql.append("	u.email, ");
		sql.append("	u.login, ");
		sql.append("	u.senha, ");
		sql.append("	u.ind_estoque ");
		sql.append("FROM usuario u ");
		sql.append("JOIN perfil p on p.perfil_id = u.perfil_id ");
		sql.append("WHERE u.login = :login and u.senha = :senha ");

		Query query = roEM.createNativeQuery(sql.toString());
		query.setParameter("login", login);
		query.setParameter("senha", senha);

		try {
			final Object[] object = (Object[]) query.getSingleResult();
			return new UsuarioPayload(object);
		} catch (NoResultException e) {
			log.info("[{}] Usuário não encontrado para os parâmetros [{}] [{}]", login, senha);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioPayload> searchUsuario(String login, String email) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.usuario_id, ");
		sql.append("	p.descricao, ");
		sql.append("	u.nome, ");
		sql.append("	u.email, ");
		sql.append("	u.login, ");
		sql.append("	u.senha, ");
		sql.append("	u.ind_estoque ");
		sql.append("FROM usuario u ");
		sql.append("JOIN perfil p on p.perfil_id = u.perfil_id ");
		sql.append("WHERE (u.login = :login OR u.email = :email) ");

		Query query = roEM.createNativeQuery(sql.toString());
		query.setParameter("login", login);
		query.setParameter("email", email);

		final List<Object[]> list = query.getResultList();
		return list.stream().map(UsuarioPayload::new).collect(Collectors.toList());
	}

	public void intaivaSessaoUsuario(String token) {
		List<UsuarioSession> sessions = sessionRepository.findByTokenAccess(token);

		if (sessions.isEmpty()) {
			log.info("Nenhuma sessão ativa encontrada para o token {}", token);
			return;
		}

		sessions.forEach(session -> session.setIndSession(false));
		sessionRepository.saveAll(sessions);

		log.info("Sessões inativadas para o token {}", token);
	}

}
