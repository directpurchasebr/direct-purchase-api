package br.com.directpurchase.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "PRODUTO")
@SequenceGenerator(name = "PRODUTO_SEQ", sequenceName = "PRODUTO_SEQ", allocationSize = 1)
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PRODUTO_SEQ")
	@Column(name = "PRODUTO_ID")
	private Integer produtoId;

	@JoinColumn(name = "FORNECEDOR_ID", referencedColumnName = "FORNECEDOR_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Fornecedor fornecedor;

	@Column(name = "CODIGO")
	private String codigo;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "MARCA")
	private String marca;

	@Column(name = "UNIDADE")
	private String unidade;

	@Column(name = "PRECO")
	private BigDecimal preco;

	@Basic(optional = false)
	@Column(name = "DATA_MODIF")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dataModif;

	@Column(name = "USUARIOMODIF_ID")
	private long usuarioModifId;

	public Produto(Integer produtoId) {
		this.produtoId = produtoId;
	}

}
