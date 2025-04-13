package br.com.directpurchase.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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

	@OneToOne
	@JoinColumn(name = "PESSOA_ID", referencedColumnName = "PESSOA_ID", nullable = false, unique = true)
	private Pessoa pessoa;

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
