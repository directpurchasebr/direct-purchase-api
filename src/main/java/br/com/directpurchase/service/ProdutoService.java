package br.com.directpurchase.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.repository.ProdutoRepository;
import br.com.directpurchase.response.ProdutoResponse;
import br.com.directpurchase.transform.ProdutoTransform;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoTransform produtoTransform;

	@Autowired
	private AuthUtils authUtils;

	public List<ProdutoResponse> buscaProdutos(String descricao) {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();

		List<Produto> entitys = produtoRepository.buscaPorDescricao(descricao, usuario.getFornecedores());
		return entitys.stream().map(e -> produtoTransform.transform(e)).collect(Collectors.toList());
	}

	public List<ProdutoResponse> listarProdutos() {
		
		
		Iterable<Produto> entitys = produtoRepository.findAll();
		return StreamSupport.stream(entitys.spliterator(), false).map(e -> produtoTransform.transform(e))
		        .collect(Collectors.toList());
		
	}

}
