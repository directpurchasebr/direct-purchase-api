package br.com.directpurchase.dto;

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
public class PessoaBancoDto {
    private Integer pessoaBancoId;
    private String banco;
    private String agencia;
    private String conta;
    private String tipoConta;
    private String titular;
    private String cnpjTitular;
}
