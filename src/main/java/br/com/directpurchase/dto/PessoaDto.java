package br.com.directpurchase.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class PessoaDto {

    private Integer pessoaId;
    private Integer negocioId;
    private String codigo;
    private String nome;
    private String nomeFantasia;
    private String cpf;
    private String cnpj;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private String telefone;
    private String email;
    private String site;
    private String responsavel;
    private String telefoneResponsavel;
    private String observacoes;
    private List<PessoaEnderecoDto> enderecos;
    private List<PessoaBancoDto> bancos;
}
