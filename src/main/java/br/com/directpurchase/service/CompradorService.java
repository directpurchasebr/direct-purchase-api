package br.com.directpurchase.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dto.CompradorDto;
import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.repository.CompradorRepository;
import br.com.directpurchase.transform.UsuarioTransform;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompradorService {

	@Autowired
	private UsuarioTransform usuarioTransform;

	@Autowired
	private AuthUtils authUtils;

	@Autowired
	private CompradorRepository compradorRepository;

	public List<CompradorDto> listarCompradores() {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();

		List<Comprador> entitys = compradorRepository.buscaPorCompradores(usuario.getCompradores());
		return entitys.stream().map(e -> usuarioTransform.transform(e)).collect(Collectors.toList());
	}

}
