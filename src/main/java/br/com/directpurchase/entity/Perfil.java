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
@Table(name = "PERFIL")
@SequenceGenerator(name = "PERFIL_SEQ", sequenceName = "PERFIL_SEQ", allocationSize = 1)
public class Perfil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PERFIL_SEQ")
	@Column(name = "PERFIL_ID")
	private Integer perfilId;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "IND_ADMIN")
	private Boolean indAdmin;

	@Column(name = "IND_REGULAR")
	private Boolean indRegular;

	@Column(name = "IND_SUPORTE")
	private Boolean indSuporte;

	@Column(name = "IND_VENDA")
	private Boolean indvenda;

	public Perfil(Integer perfilId) {
		this.perfilId = perfilId;
	}

}
