package br.com.directpurchase.dto;

import java.math.BigDecimal;
import java.util.List;

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
public class PedidoDto {

    private Integer pedidoId;
    private String codigoPedido;
    private CompradorDto comprador;
    private List<ProdutoPedidoDto> produtos;
    private BigDecimal valorTotal;
    private String observacao;
    private String status;
}
