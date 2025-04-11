package br.com.directpurchase.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dao.UsuarioDao;
import br.com.directpurchase.dto.UsuarioDto;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.response.Status;
import br.com.directpurchase.transform.UsuarioTransform;

@Service
public class UsuarioService {

	private final UsuarioTransform usuarioTransform;
	private final UsuarioDao usuarioDao;
	private final AuthUtils authUtils;

	public UsuarioService(UsuarioTransform usuarioTransform, UsuarioDao usuarioDao, AuthUtils authUtils) {
		this.usuarioTransform = usuarioTransform;
		this.usuarioDao = usuarioDao;
		this.authUtils = authUtils;
	}

	public Status salvarUsuario(UsuarioDto dto) throws ValidationException {
		Usuario entity = usuarioTransform.transform(dto);

		if (entity.getUsuarioId() == null && usuarioJaExiste(dto)) {
			throw new ValidationException("Já existe usuário com e-mail e login cadastrados!");
		}

		usuarioDao.salvar(entity);
		UsuarioDto responseDto = usuarioTransform.transform(entity);

		return new Status(true, "Usuário cadastrado com sucesso", "", responseDto);
	}

	public UsuarioDto get() throws ValidationException {
		UsuarioPayload usuarioLogado = authUtils.getUsuarioLogado();
		if (usuarioLogado == null) {
			throw new ValidationException("Usuário não está logado!");
		}

		return usuarioTransform.fetchUsuarioDto(usuarioLogado.getUsuarioId());
	}

	private boolean usuarioJaExiste(UsuarioDto dto) {
		List<UsuarioPayload> existentes = usuarioDao.searchUsuario(dto.getLogin(), dto.getEmail());
		return existentes != null && !existentes.isEmpty();
	}
}
