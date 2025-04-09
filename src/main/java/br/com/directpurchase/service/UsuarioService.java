package br.com.directpurchase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dao.UsuarioDao;
import br.com.directpurchase.dto.UsuarioDto;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.response.Status;
import br.com.directpurchase.transform.UsuarioTransform;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

	@Autowired
	private UsuarioTransform usuarioTransform;

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private AuthUtils authUtils;

	public Status salvarUsuario(UsuarioDto bean) throws ValidationException {

		Usuario entity = usuarioTransform.transform(bean);
		if (entity.getUsuarioId() == null) {

			// FIXME: O objeto UsuarioPayload deve ser usado para o controle do usuario
			// logado o objeto aqui retornodo deve ser pra enviar UsuarioDto
			List<UsuarioPayload> list = usuarioDao.searchUsuario(bean.getLogin(), bean.getEmail());
			if (list != null && !list.isEmpty()) {
				throw new ValidationException("Ja existe usuario com email e login ja cadastrado!");
			}
		}

		usuarioDao.salvar(entity);
		UsuarioDto resp = usuarioTransform.transform(entity);

		return new Status(Boolean.TRUE, "Usuario cadastrado com sucesso", "", resp);
	}

	public UsuarioDto get() throws ValidationException {

		UsuarioPayload usuarioLogado = authUtils.getUsuarioLogado();
		if (usuarioLogado == null) {
			throw new ValidationException("Usuario não esta logado!");
		}

		return usuarioTransform.fetchUsuarioDto(usuarioLogado.getUsuarioId());
	}
}
