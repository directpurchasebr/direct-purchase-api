package br.com.directpurchase.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dto.CompradorDto;
import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.repository.CompradorRepository;
import br.com.directpurchase.transform.UsuarioTransform;

@Service
public class CompradorService {

	private final UsuarioTransform usuarioTransform;
	private final AuthUtils authUtils;
	private final CompradorRepository compradorRepository;

	public CompradorService(UsuarioTransform usuarioTransform, AuthUtils authUtils,
			CompradorRepository compradorRepository) {
		this.usuarioTransform = usuarioTransform;
		this.authUtils = authUtils;
		this.compradorRepository = compradorRepository;
	}

	public List<CompradorDto> listarCompradores() throws ValidationException {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();
		List<Comprador> compradores = compradorRepository.buscaPorCompradores(usuario.getCompradores());
		return compradores.stream().map(usuarioTransform::transform).toList();
	}
}
