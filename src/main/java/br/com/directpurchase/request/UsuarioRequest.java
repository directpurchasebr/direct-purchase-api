package br.com.directpurchase.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class UsuarioRequest {

	@JsonIgnore
	private Integer usuarioId;
	private String nome;
	private String email;
	private String login;

	@JsonIgnore
	private String senha;
	private Boolean indEstoque;

	@JsonIgnore
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;

	private PerfilRequest perfil;

	private List<FornecedorRequest> fornecedores;
	private List<CompradorRequest> compradores;
}
