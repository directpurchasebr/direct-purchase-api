package br.com.directpurchase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.dao.UsuarioDao;
import br.com.directpurchase.dto.UsuarioDto;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.repository.UsuarioRepository;
import br.com.directpurchase.request.SearchUsuarioRequest;
import br.com.directpurchase.response.Status;
import br.com.directpurchase.transform.UsuarioTransform;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioTransform usuarioTransform;

	@Autowired
	private UsuarioDao usuarioDao;

	public Status salvarUsuario(UsuarioDto bean) {
		Usuario entity = usuarioTransform.transform(bean);
		usuarioRepository.save(entity);
		UsuarioDto resp = usuarioTransform.transform(entity);

		return new Status(Boolean.TRUE, "Usuario cadastrado com sucesso", "", resp);
	}

	public List<UsuarioPayload> consultarUsuario(SearchUsuarioRequest bean) {
		return usuarioDao.searchUsuario(bean.getLogin(), bean.getNome(), bean.getEmail());
	}
}
