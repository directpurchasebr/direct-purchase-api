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
@Builder
@ToString
public class ProdutoPedidoDto {

    private ProdutoDto produto;
    private FornecedorDto fornecedor;
    private String codigo;
    private String descricaoProduto;
    private Integer quantidade;
    private String unidade;
    private BigDecimal preco;
    private BigDecimal precoTotal;

}
