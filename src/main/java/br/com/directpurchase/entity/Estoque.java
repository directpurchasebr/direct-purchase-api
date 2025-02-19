package br.com.directpurchase.entity;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "ESTOQUE")
@SequenceGenerator(name = "ESTOQUE_SEQ", sequenceName = "ESTOQUE_SEQ", allocationSize = 1)
public class Estoque implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ESTOQUE_SEQ")
	@Column(name = "ESTOQUE_ID")
	private Integer estoqueId;

	@JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	@JoinColumn(name = "PRODUTO_ID", referencedColumnName = "PRODUTO_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;
	
	@Column(name = "QUANTIDADE")
	private Integer quantidade;

	@Column(name = "PRECO")
	private BigDecimal preco;
	
}
