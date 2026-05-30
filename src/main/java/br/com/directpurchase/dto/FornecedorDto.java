package br.com.directpurchase.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
public class FornecedorDto extends PessoaDto {

	private Integer fornecedorId;
	private String layoutExcel;
}
