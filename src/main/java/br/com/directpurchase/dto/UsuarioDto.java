package br.com.directpurchase.dto;

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
public class UsuarioDto {

	private Long usuarioId;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private Boolean indEstoque;
	private Long perfilId;
	private String status;

	public UsuarioDto(Object... fields) {
		this.usuarioId = (Long) fields[0];
		this.perfilId = (Long) fields[1];
		this.nome = (String) fields[2];
		this.email = (String) fields[3];
		this.login = (String) fields[4];
		this.senha = (String) fields[5];
		this.indEstoque = (Boolean) fields[6];

	}
}
