package br.com.directpurchase.transform;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.response.FornecedorResponse;
import br.com.directpurchase.response.ProdutoResponse;

@Component
public class ProdutoTransform {

	public ProdutoResponse transform(Produto entity) {
		return ProdutoResponse.builder()
				.produtoId(entity.getProdutoId())
				.codigo(removerEspacos(entity.getCodigo()))
				.descricao(removerEspacos(entity.getDescricao()))
				.unidade(removerEspacos(entity.getUnidade()))
				.preco(entity.getPreco())
				.fornecedor(transform(entity.getFornecedor()))
				.build();
	}

	public FornecedorResponse transform(Fornecedor entity) {
		return FornecedorResponse.builder()
				.fornecedorId(entity.getFornecedorId())
				.codigo(entity.getCodigo())
				.nome(entity.getNome())
				.build();
	}

	private String removerEspacos(String valor) {
		return Optional.ofNullable(valor).map(String::strip).orElse(null);
	}
}
