package br.com.directpurchase.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dto.PerfilDto;
import br.com.directpurchase.repository.PerfilRepository;
import br.com.directpurchase.transform.UsuarioTransform;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PerfilService {

	@Autowired
	private UsuarioTransform usuarioTransform;

	@Autowired
	private AuthUtils authUtils;

	@Autowired
	private PerfilRepository perfilRepository;

	public List<PerfilDto> listarPerfil() {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();
		List<PerfilDto> prefils = StreamSupport.stream(perfilRepository.findAll().spliterator(), false)
		        .map(p -> usuarioTransform.transform(p)).collect(Collectors.toList());
		switch (usuario.getPerfil()) {
			case "ADMIN":
				return prefils;
			case "USER":
				return prefils.stream().filter(f -> f.getPerfilId() == 2).collect(Collectors.toList());
			default:
				return prefils.stream().filter(f -> (f.getPerfilId() != 1)).collect(Collectors.toList());
		}
	}

}
