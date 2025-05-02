package br.com.directpurchase.service;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.dao.AuthDao;
import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dto.UsuarioDto;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.response.Status;
import br.com.directpurchase.transform.UsuarioTransform;

@Service
public class UsuarioService {

	private final UsuarioTransform usuarioTransform;
	private final AuthDao usuarioDao;
	private final AuthUtils authUtils;

	public UsuarioService(UsuarioTransform usuarioTransform, AuthDao usuarioDao, AuthUtils authUtils) {
		this.usuarioTransform = usuarioTransform;
		this.usuarioDao = usuarioDao;
		this.authUtils = authUtils;
	}

	public Status salvarUsuario(UsuarioDto dto) throws ValidationException {
		Usuario entity = usuarioTransform.transform(dto);
		usuarioDao.salvar(entity);
		UsuarioDto responseDto = usuarioTransform.transform(entity);
		return new Status(true, "Usuário cadastrado com sucesso", "", responseDto);
	}

	public UsuarioDto get() throws ValidationException {
		UsuarioPayload usuarioLogado = authUtils.getUsuarioLogado();
		return usuarioTransform.fetchUsuarioDto(usuarioLogado.getUsuarioId());
	}

}
