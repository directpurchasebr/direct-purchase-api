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
@Table(name = "PESSOA_ENDERECO")
@SequenceGenerator(name = "PESSOAENDERECO_SEQ", sequenceName = "PESSOAENDERECO_SEQ", allocationSize = 1)
public class PessoaEndereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PESSOAENDERECO_SEQ")
    @Column(name = "PESSOAENDERECO_ID")
    private Integer pessoaEnderecoId;

    @ManyToOne
    @JoinColumn(name = "PESSOA_ID", referencedColumnName = "PESSOA_ID", nullable = false, unique = true)
    private Pessoa pessoa;

    @Column(name = "LOGRADOURO")
    private String logradouro;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "CEP")
    private String cep;

}
