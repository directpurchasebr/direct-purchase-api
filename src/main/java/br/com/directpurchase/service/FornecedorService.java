package br.com.directpurchase.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dto.FornecedorDto;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.repository.FornecedorRespository;
import br.com.directpurchase.transform.UsuarioTransform;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FornecedorService {

	@Autowired
	private UsuarioTransform usuarioTransform;

	@Autowired
	private FornecedorRespository fornecedorRespository;

	@Autowired
	private AuthUtils authUtils;

	public List<FornecedorDto> listarFornecedores() {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();

		List<Fornecedor> entitys = fornecedorRespository.buscaPorFornecedores(usuario.getFornecedores());
		return entitys.stream().map(e -> usuarioTransform.transform(e)).collect(Collectors.toList());
	}

}
