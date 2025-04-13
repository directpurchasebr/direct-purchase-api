package br.com.directpurchase.entity;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "PESSOA_BANCO")
@SequenceGenerator(name = "PESSOABANCO_SEQ", sequenceName = "PESSOABANCO_SEQ", allocationSize = 1)
public class PessoaBanco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PESSOABANCO_SEQ")
    @Column(name = "PESSOABANCO_ID")
    private Integer pessoaBancoId;

    @ManyToOne
    @JoinColumn(name = "PESSOA_ID", referencedColumnName = "PESSOA_ID", nullable = false, unique = true)
    private Pessoa pessoa;

    @Column(name = "BANCO")
    private String banco;

    @Column(name = "AGENCIA")
    private String agencia;

    @Column(name = "CONTA")
    private String conta;

    @Column(name = "TIPO_CONTA")
    private String tipoConta;

    @Column(name = "TITULAR")
    private String titular;

    @Column(name = "CNPJ_TITULAR")
    private String cnpjTitular;
}
