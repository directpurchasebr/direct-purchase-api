package br.com.directpurchase.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dao.ProdutoDao;
import br.com.directpurchase.dto.ProdutoDto;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.repository.ProdutoRepository;
import br.com.directpurchase.response.Status;
import br.com.directpurchase.transform.ProdutoTransform;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	private final ProdutoTransform produtoTransform;
	private final AuthUtils authUtils;
	private final ProdutoDao produtoDao;

	public ProdutoService(ProdutoRepository produtoRepository, ProdutoTransform produtoTransform, AuthUtils authUtils,
			ProdutoDao produtoDao) {
		this.produtoRepository = produtoRepository;
		this.produtoTransform = produtoTransform;
		this.authUtils = authUtils;
		this.produtoDao = produtoDao;
	}

	public List<ProdutoDto> buscaProdutos(String descricao) {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();

		return produtoRepository.buscaPorDescricao(usuario.getUsuarioId(), descricao, usuario.getFornecedores())
				.stream().map(produtoTransform::transform).toList();
	}

	public List<ProdutoDto> listarProdutos() {
		return StreamSupport.stream(produtoRepository.findAll().spliterator(), false)
				.map(produtoTransform::transform).toList();
	}

	public Status salvarProduto(ProdutoDto request) {
		Produto entity = produtoTransform.transform(request);
		produtoDao.salvar(entity);
		ProdutoDto responseDto = produtoTransform.transform(entity);
		return new Status(true, "Produto cadastrado com sucesso", "", responseDto);
	}
}
