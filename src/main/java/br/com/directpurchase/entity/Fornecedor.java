package br.com.directpurchase.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Table(name = "FORNECEDOR")
@SequenceGenerator(name = "FORNECEDOR_SEQ", sequenceName = "FORNECEDOR_SEQ", allocationSize = 1)
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FORNECEDOR_SEQ")
	@Column(name = "FORNECEDOR_ID")
	private Integer fornecedorId;

	@JoinColumn(name = "NEGOCIO_ID", referencedColumnName = "NEGOCIO_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Negocio negocio;

	@Column(name = "CODIGO")
	private String codigo;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "NOME_FANTASIA")
	private String nomeFantasia;

	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "ENDERECO")
	private String endereco;

	@Column(name = "IMAGEM")
	private String imagem;

	@Column(name = "LAYOUT_EXCEL")
	private String layoutExcel;

	@Column(name = "IND_LAYOUT_DEFAULT")
	private Boolean indLayoutDefault;

	@ManyToMany(mappedBy = "fornecedores")
	private List<Usuario> usuarios;

	public Fornecedor(Integer fornecedorId) {
		this.fornecedorId = fornecedorId;
	}

}
