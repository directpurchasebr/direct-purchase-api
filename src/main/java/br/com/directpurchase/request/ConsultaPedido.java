package br.com.directpurchase.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class ConsultaPedido {

    private String codigoPedido;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPedido;
    private Integer compradorId;
}
