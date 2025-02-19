package br.com.directpurchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.repository.UsuarioRepository;
import br.com.directpurchase.request.UsuarioRequest;
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

	public Status salvarUsuario(UsuarioRequest bean) {
		try {
			Usuario entity = usuarioTransform.transform(bean);
			usuarioRepository.save(entity);
		} catch (Exception e) {
			log.error("", e);
			return new Status(Boolean.FALSE, "", "ERRO ao Cadastro novo Usuario");
		}

		return new Status(Boolean.TRUE, "Usuario cadastrado com sucesso", "");
	}

}
