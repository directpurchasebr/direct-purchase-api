package br.com.directpurchase.transform;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.directpurchase.dto.FornecedorDto;
import br.com.directpurchase.dto.ProdutoDto;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Produto;

@Component
public class ProdutoTransform {

	public ProdutoDto transform(Produto entity) {

		StringBuilder descricao = new StringBuilder();
		descricao.append(null == entity.getDescricao() ? "" : removerEspacos(entity.getDescricao().toUpperCase()));
		descricao.append(" - ");
		descricao.append(null == entity.getFornecedor() ? ""
				: removerEspacos(entity.getFornecedor().getPessoa().getNomeFantasia()));

		return ProdutoDto.builder()
				.produtoId(entity.getProdutoId())
				.codigo(removerEspacos(entity.getCodigo()))
				.descricao(descricao.toString())
				.unidade(removerEspacos(entity.getUnidade()))
				.preco(entity.getPreco())
				.fornecedor(transform(entity.getFornecedor()))
				.build();
	}

	public FornecedorDto transform(Fornecedor entity) {

		String nome = Optional.ofNullable(entity.getPessoa().getNomeFantasia())
				.orElse(entity.getPessoa().getNome());

		return FornecedorDto.builder()
				.fornecedorId(entity.getFornecedorId())
				.codigo(entity.getPessoa().getCodigo())
				.nome(nome)
				.build();
	}

	private String removerEspacos(String valor) {
		return Optional.ofNullable(valor).map(String::strip).orElse(null);
	}
}
