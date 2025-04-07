package br.com.directpurchase.auth.payload;

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
@ToString
@Builder
public class UsuarioPayload {

	private Integer usuarioId;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private Boolean indEstoque;
	private Integer perfilId;
	private String status;
	private List<Integer> fornecedores;
	private List<Integer> compradores;

	public UsuarioPayload(Object... fields) {
		this.usuarioId = (Integer) fields[0];
		this.perfilId = (Integer) fields[1];
		this.nome = (String) fields[2];
		this.email = (String) fields[3];
		this.login = (String) fields[4];
		this.senha = (String) fields[5];
		this.indEstoque = (Boolean) fields[6];
	}
}
