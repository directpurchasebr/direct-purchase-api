package br.com.directpurchase.dto;

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
@ToString
@Builder
public class CompradorDto {

	private Integer compradorId;
	private Integer negocioId;
	private String codigo;
	private String nome;
}
