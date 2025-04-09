package br.com.directpurchase.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UsuarioDao {

	@Autowired
	private EntityManager roEM;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public void salvar(Usuario entity) {
		usuarioRepository.save(entity);
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

}
