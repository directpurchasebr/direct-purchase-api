package br.com.directpurchase.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class PedidoDto {

	private Integer pedidoId;
	private List<PedidoProdutoDto> pedidos;
	private Integer usuarioId;
	private Integer compradorId;
	private String codigoPedido;
	private BigDecimal precoTotal;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataPedido;
}
