package br.com.directpurchase.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dto.FornecedorDto;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.repository.FornecedorRespository;
import br.com.directpurchase.transform.UsuarioTransform;

@Service
public class FornecedorService {

	private final UsuarioTransform usuarioTransform;
	private final FornecedorRespository fornecedorRespository;
	private final AuthUtils authUtils;

	public FornecedorService(UsuarioTransform usuarioTransform, FornecedorRespository fornecedorRespository,
			AuthUtils authUtils) {
		this.usuarioTransform = usuarioTransform;
		this.fornecedorRespository = fornecedorRespository;
		this.authUtils = authUtils;
	}

	public List<FornecedorDto> listarFornecedores() throws ValidationException {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();
		List<Fornecedor> entitys = fornecedorRespository.buscaPorFornecedores(usuario.getFornecedores());
		return entitys.stream().map(usuarioTransform::transform).toList();
	}
}