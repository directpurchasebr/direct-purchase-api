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
@Table(name = "PEDIDO_PRODUTO")
@SequenceGenerator(name = "PEDIDOPRODUTO_SEQ", sequenceName = "PEDIDOPRODUTO_SEQ", allocationSize = 1)
public class PedidoProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PEDIDOPRODUTO_SEQ")
	@Column(name = "PEDIDOPRODUTO_ID")
	private Integer pedidoProdutoId;

	@JoinColumn(name = "PEDIDO_ID", referencedColumnName = "PEDIDO_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Pedido pedido;

	@JoinColumn(name = "PRODUTO_ID", referencedColumnName = "PRODUTO_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;

	@Column(name = "QUANTIDADE")
	private Integer quantidade;

	@Column(name = "PRECO")
	private BigDecimal preco;

	@Column(name = "PRECO_ORIGINAL")
	private BigDecimal precoOriginal;

	@JoinColumn(name = "ESTOQUE_ID", referencedColumnName = "ESTOQUE_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Estoque estoque;

	@Column(name = "IND_ESTOQUE")
	private Boolean indEstoque;

}
