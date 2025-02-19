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
public class TemplateThree {

	private String codigo;
	private String descricao;
	private String embalagem;
	private String marca;
	private String quantidade;
	private BigDecimal valor;
	private String caixa;
}
