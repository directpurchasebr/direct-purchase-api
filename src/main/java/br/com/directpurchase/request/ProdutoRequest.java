package br.com.directpurchase.request;

import java.math.BigDecimal;

import br.com.directpurchase.dto.FornecedorDto;
import br.com.directpurchase.response.ProdutoResponse;
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
public class ProdutoRequest {

    private ProdutoResponse produto;
    private FornecedorDto fornecedor;
    private String codigo;
    private String descricaoProduto;
    private Integer quantidade;
    private String unidade;
    private BigDecimal preco;
    private BigDecimal precoTotal;

}
