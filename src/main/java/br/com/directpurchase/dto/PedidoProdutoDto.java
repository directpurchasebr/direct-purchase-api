package br.com.directpurchase.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PedidoProdutoDto {

	private Integer pedidoProdutoId;
	private Integer pedidoId;
	private Integer produtoId;
	private Integer quantidade;
	private BigDecimal preco;
	private Integer estoqueId;
	private Boolean indEstoque;

}
