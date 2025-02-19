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
public class CompradorRequest {

	private Integer compradorId;
	private Integer negocioId;
	private String codigo;
	private String nome;
}
