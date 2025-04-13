package br.com.directpurchase.entity;

import java.io.Serializable;
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
@Table(name = "PESSOA")
@SequenceGenerator(name = "PESSOA_SEQ", sequenceName = "PESSOA_SEQ", allocationSize = 1)
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PESSOA_SEQ")
    @Column(name = "PESSOA_ID")
    private Integer pessoaId;

    @JoinColumn(name = "NEGOCIO_ID", referencedColumnName = "NEGOCIO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Negocio negocio;

    @Column(name = "TIPO_PESSOA", nullable = false)
    private String tipoPessoa; // "FISICA" ou "JURIDICA"

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "NOME_FANTASIA")
    private String nomeFantasia;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "INSCRICAO_ESTADUAL")
    private String inscricaoEstadual;

    @Column(name = "INSCRICAO_MUNICIPAL")
    private String inscricaoMunicipal;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SITE")
    private String site;

    @Column(name = "RESPONSAVEL")
    private String responsavel;

    @Column(name = "TELEFONE_RESPONSAVEL")
    private String telefoneResponsavel;

    @Column(name = "OBSERVACOES")
    private String observacoes;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @Column(name = "DATA_MODIF", updatable = false)
    private LocalDateTime dataModif;

    @Column(name = "DATA_CADASTRO")
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PessoaEndereco> enderecos;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PessoaBanco> dadosBancarios;

}
