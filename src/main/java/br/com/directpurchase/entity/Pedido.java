package br.com.directpurchase.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "PEDIDO")
@SequenceGenerator(name = "PEDIDO_SEQ", sequenceName = "PEDIDO_SEQ", allocationSize = 1)
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PEDIDO_SEQ")
	@Column(name = "PEDIDO_ID")
	private Integer pedidoId;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<PedidoProduto> pedidoProdutos;

	@JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	@JoinColumn(name = "COMPRADOR_ID", referencedColumnName = "COMPRADOR_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Comprador comprador;

	@Column(name = "CODIGO_PEDIDO")
	private String codigoPedido;

	@Column(name = "PRECO_TOTAL")
	private BigDecimal precoTotal;

	@Basic(optional = false)
	@Column(name = "DATA_PEDIDO")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dataPedido;

	public Pedido(Integer pedidoId) {
		super();
		this.pedidoId = pedidoId;
	}

	
}
