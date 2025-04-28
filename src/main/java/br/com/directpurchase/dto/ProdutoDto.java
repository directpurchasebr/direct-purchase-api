package br.com.directpurchase.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProdutoDto {

	private Integer produtoId;
	private String codigo;
	private String descricao;
	private String marca;
	private String unidade;
	private BigDecimal preco;
	private FornecedorDto fornecedor;
}
