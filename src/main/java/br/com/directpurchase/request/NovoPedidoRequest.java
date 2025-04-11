package br.com.directpurchase.request;

import java.math.BigDecimal;
import java.util.List;

import br.com.directpurchase.dto.CompradorDto;
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
public class NovoPedidoRequest {

    private Integer pedidoId;
    private String codigoPedido;
    private CompradorDto comprado;
    private List<ProdutoRequest> produtos;
    private BigDecimal valorTotal;
    private String observacao;
    private String status;
}
