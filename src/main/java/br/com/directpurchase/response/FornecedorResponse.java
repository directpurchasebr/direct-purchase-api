package br.com.directpurchase.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FornecedorResponse {

	private Integer fornecedorId;
	private String codigo;
	private String nome;
}
