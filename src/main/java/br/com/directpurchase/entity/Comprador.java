package br.com.directpurchase.entity;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "COMPRADOR")
@SequenceGenerator(name = "COMPRADOR_SEQ", sequenceName = "COMPRADOR_SEQ", allocationSize = 1)
public class Comprador implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "COMPRADOR_SEQ")
	@Column(name = "COMPRADOR_ID")
	private Integer compradorId;

	@OneToOne
	@JoinColumn(name = "PESSOA_ID", referencedColumnName = "PESSOA_ID", nullable = false, unique = true)
	private Pessoa pessoa;

}
