package br.com.directpurchase.request;

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
public class FornecedorRequest {

	private Integer fornecedorId;
	private String codigo;
	private String nome;
	private String layoutExcel;
}
