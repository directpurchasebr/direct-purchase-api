package br.com.directpurchase.auth.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.directpurchase.request.CompradorRequest;
import br.com.directpurchase.request.FornecedorRequest;
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

	private Integer usuarioId;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private Boolean indEstoque;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;

	private Integer perfilId;

	private List<FornecedorRequest> fornecedores;
	private List<CompradorRequest> compradores;
}
