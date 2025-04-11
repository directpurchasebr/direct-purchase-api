package br.com.directpurchase.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dto.PerfilDto;
import br.com.directpurchase.repository.PerfilRepository;
import br.com.directpurchase.transform.UsuarioTransform;
import br.com.directpurchase.type.PerfilType;

@Service
public class PerfilService {

	private final UsuarioTransform usuarioTransform;
	private final AuthUtils authUtils;
	private final PerfilRepository perfilRepository;

	public PerfilService(UsuarioTransform usuarioTransform, AuthUtils authUtils, PerfilRepository perfilRepository) {
		this.usuarioTransform = usuarioTransform;
		this.authUtils = authUtils;
		this.perfilRepository = perfilRepository;
	}

	public List<PerfilDto> listarPerfil() {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();
		List<PerfilDto> perfis = StreamSupport.stream(perfilRepository.findAll().spliterator(), false)
				.map(usuarioTransform::transform).toList();

		PerfilType perfil;
		try {
			perfil = PerfilType.valueOf(usuario.getPerfil());
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new IllegalStateException("Perfil desconhecido: " + usuario.getPerfil());
		}

		return switch (perfil) {
			case ADMIN -> perfis;
			case USER -> perfis.stream().filter(f -> f.getPerfilId() == 2).toList();
			default -> perfis.stream().filter(f -> f.getPerfilId() != 1).toList();
		};
	}
}