package br.com.directpurchase.dao;

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

	public UsuarioDto findByUsuarioLoginSenha(String login, String senha) {

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

}
