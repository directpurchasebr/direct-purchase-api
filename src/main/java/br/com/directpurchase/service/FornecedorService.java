package br.com.directpurchase.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dao.PessoaDao;
import br.com.directpurchase.dto.FornecedorDto;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.repository.FornecedorRespository;
import br.com.directpurchase.response.Status;
import br.com.directpurchase.transform.PessoaTransform;

@Service
public class FornecedorService {

	private final PessoaTransform pessoaTransform;
	private final FornecedorRespository fornecedorRespository;
	private final AuthUtils authUtils;
	private final PessoaDao pessoaDao;

	public FornecedorService(PessoaTransform pessoaTransform, FornecedorRespository fornecedorRespository,
			AuthUtils authUtils, PessoaDao pessoaDao) {
		this.pessoaTransform = pessoaTransform;
		this.fornecedorRespository = fornecedorRespository;
		this.authUtils = authUtils;
		this.pessoaDao = pessoaDao;
	}

	public List<FornecedorDto> listarFornecedores() throws ValidationException {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();
		List<Fornecedor> entitys = fornecedorRespository.buscaPorFornecedores(usuario.getFornecedores());
		return entitys.stream().map(pessoaTransform::transform).toList();
	}

	public Object salvarFornecedor(FornecedorDto request) {
		Fornecedor entity = pessoaTransform.transform(request);
		pessoaDao.salvar(entity);
		FornecedorDto responseDto = pessoaTransform.transform(entity);
		return new Status(true, "Fornecedor cadastrado com sucesso", "", responseDto);
	}
}