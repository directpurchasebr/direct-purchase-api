package br.com.directpurchase.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.repository.ProdutoRepository;
import br.com.directpurchase.response.ProdutoResponse;
import br.com.directpurchase.transform.ProdutoTransform;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	private final ProdutoTransform produtoTransform;
	private final AuthUtils authUtils;

	public ProdutoService(ProdutoRepository produtoRepository, ProdutoTransform produtoTransform, AuthUtils authUtils) {
		this.produtoRepository = produtoRepository;
		this.produtoTransform = produtoTransform;
		this.authUtils = authUtils;
	}

	public List<ProdutoResponse> buscaProdutos(String descricao) {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();

		return produtoRepository.buscaPorDescricao(descricao, usuario.getFornecedores())
				.stream().map(produtoTransform::transform).toList();
	}

	public List<ProdutoResponse> listarProdutos() {
		return StreamSupport.stream(produtoRepository.findAll().spliterator(), false)
				.map(produtoTransform::transform).toList();
	}
}
