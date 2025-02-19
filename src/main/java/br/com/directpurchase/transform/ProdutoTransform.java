package br.com.directpurchase.transform;

import org.springframework.stereotype.Component;

import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.response.ProdutoResponse;

@Component
public class ProdutoTransform {

	public ProdutoResponse transform(Produto entity) {
		return ProdutoResponse.builder().codigo(entity.getCodigo()).descricao(entity.getDescricao())
				.unidade(entity.getUnidade()).preco(entity.getPreco()).build();
	}

}
