package br.com.directpurchase.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
public class PedidoFornecedorDto {

    private Integer pedidoId;
    private String codigoPedido;
    private LocalDateTime dataPedido;
    private BigDecimal valorTotal;
    private String observacao;
    private String status;
    private String descricaoComprador;
    private CompradorDto comprador;
    private Map<String, List<ProdutoPedidoDto>> pedidosFornecedor;
}
