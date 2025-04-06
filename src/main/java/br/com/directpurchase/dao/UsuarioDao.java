package br.com.directpurchase.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.directpurchase.auth.dto.UsuarioDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UsuarioDao {

	@Autowired
	private EntityManager roEM;

	public UsuarioDto findUsuarioByLoginSenha(String login, String senha) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.usuario_id, ");
		sql.append("	u.perfil_id, ");
		sql.append("	u.nome, ");
		sql.append("	u.email, ");
		sql.append("	u.login, ");
		sql.append("	u.senha, ");
		sql.append("	u.ind_estoque ");
		sql.append("FROM usuario u ");
		sql.append("WHERE u.login = :login and u.senha = :senha ");

		Query query = roEM.createNativeQuery(sql.toString());
		query.setParameter("login", login);
		query.setParameter("senha", senha);

		try {
			final Object[] object = (Object[]) query.getSingleResult();
			return new UsuarioDto(object);
		} catch (NoResultException e) {
			log.info("[{}] Usuário não encontrado para os parâmetros [{}] [{}]", login, senha);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioDto> searchUsuario(String login, String nome, String email) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.usuario_id, ");
		sql.append("	u.perfil_id, ");
		sql.append("	u.nome, ");
		sql.append("	u.email, ");
		sql.append("	u.login, ");
		sql.append("	u.senha, ");
		sql.append("	u.ind_estoque ");
		sql.append("FROM usuario u ");

		sql.append("WHERE 1 = 1 ");
		sql.append(login != null ? " and u.login = :login " : "");
		sql.append(login != null ? " and u.nome LIKE ':nome' " : "");
		sql.append(login != null ? " and u.email = :email " : "");

		Query query = roEM.createNativeQuery(sql.toString());

		if (login != null) {
			query.setParameter("login", login);
		}

		if (nome != null) {
			query.setParameter("nome", nome);
		}

		if (email != null) {
			query.setParameter("email", email);
		}

		try {
			final List<Object[]> list = query.getResultList();
			return list.stream().map(UsuarioDto::new).collect(Collectors.toList());
		} catch (NoResultException e) {
			log.info("[{}] [{}] [{}] Usuário não encontrado para os parâmetros [{}] [{}] [{}]", login, nome, email);
		}

		return null;
	}
}
