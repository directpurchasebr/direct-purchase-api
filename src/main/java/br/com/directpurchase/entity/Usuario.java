package br.com.directpurchase.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

@Entity
@Table(name = "USUARIO")
@SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ", allocationSize = 1)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "USUARIO_SEQ")
	@Column(name = "USUARIO_ID")
	private Integer usuarioId;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "LOGIN")
	private String login;

	@Column(name = "SENHA")
	private String senha;

	@Basic(optional = false)
	@Column(name = "DATA_NASCIMENTO")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dataNascimento;

	@Basic(optional = false)
	@Column(name = "DATA_MODIF")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dataModif;

	@Basic(optional = false)
	@Column(name = "DATA_CADASTRO")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dataCadastro;

	@Column(name = "IND_ESTOQUE")
	private Boolean indEstoque;

	@ManyToMany
	@JoinTable(name = "FORNECEDOR_USUARIO", joinColumns = @JoinColumn(name = "USUARIO_ID"), inverseJoinColumns = @JoinColumn(name = "FORNECEDOR_ID"))
	private List<Fornecedor> fornecedores;

	@ManyToMany
	@JoinTable(name = "COMPRADOR_USUARIO", joinColumns = @JoinColumn(name = "USUARIO_ID"), inverseJoinColumns = @JoinColumn(name = "COMPRADOR_ID"))
	private List<Comprador> compradores;

	@JoinColumn(name = "PERFIL_ID", referencedColumnName = "PERFIL_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Perfil perfil;

	public Usuario(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}
