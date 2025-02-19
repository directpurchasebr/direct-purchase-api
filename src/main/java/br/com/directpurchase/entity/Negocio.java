package br.com.directpurchase.entity;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "NEGOCIO")
@SequenceGenerator(name = "NEGOCIO_SEQ", sequenceName = "NEGOCIO_SEQ", allocationSize = 1)
public class Negocio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "NEGOCIO_SEQ")
	@Column(name = "NEGOCIO_ID")
	private Integer negocioId;

	@Column(name = "DESCRICAO")
	private String descricao;

	public Negocio(Integer negocioId) {
		this.negocioId = negocioId;
	}

}
