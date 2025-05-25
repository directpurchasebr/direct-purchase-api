package br.com.directpurchase.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.dao.AuthDao;
import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dao.EntitysFetchDao;
import br.com.directpurchase.dao.PessoaDao;
import br.com.directpurchase.dto.FornecedorDto;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Usuario;
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
	private final EntitysFetchDao entitysFetchDao;
	private final AuthDao authDao;

	public FornecedorService(PessoaTransform pessoaTransform, FornecedorRespository fornecedorRespository,
			AuthUtils authUtils, PessoaDao pessoaDao, EntitysFetchDao entitysFetchDao, AuthDao authDao) {
		this.pessoaTransform = pessoaTransform;
		this.fornecedorRespository = fornecedorRespository;
		this.authUtils = authUtils;
		this.pessoaDao = pessoaDao;
		this.entitysFetchDao = entitysFetchDao;
		this.authDao = authDao;
	}

	public List<FornecedorDto> listarFornecedores() throws ValidationException {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();
		List<Fornecedor> entitys = fornecedorRespository.buscaPorUsuario(usuario.getUsuarioId());
		return entitys.stream().map(pessoaTransform::transform).toList();
	}

	public Status salvarFornecedor(FornecedorDto request) {
		Fornecedor entity = pessoaTransform.transform(request);
		pessoaDao.salvar(entity.getPessoa());
		pessoaDao.salvar(entity);

		UsuarioPayload usuarioPayload = authUtils.getUsuarioLogado();
		Usuario usuario = entitysFetchDao.findUsuarioById(usuarioPayload.getUsuarioId());
		usuario.getFornecedores().add(entity);

		authDao.salvar(usuario);

		FornecedorDto responseDto = pessoaTransform.transform(entity);
		return new Status(true, "Fornecedor cadastrado com sucesso", "", responseDto);
	}
}