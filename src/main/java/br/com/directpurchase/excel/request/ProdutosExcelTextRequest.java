package br.com.directpurchase.excel.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutosExcelTextRequest {

	private Integer fornecedorId;
	private String filePath;
}
