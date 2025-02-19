package br.com.directpurchase.poi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.directpurchase.dto.TemplateTwo;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.util.ExcelConstants;

@Service
public class ImportTamplateTwo implements ImportTamplate<TemplateTwo> {

	@Override
	public List<TemplateTwo> convertTemplate(Map<Integer, List<Object>> excel) {

		List<TemplateTwo> resp = new ArrayList<>();

		for (Map.Entry<Integer, List<Object>> entry : excel.entrySet()) {
			List<Object> v = entry.getValue();

			int i = 0;
			TemplateTwo two = null;
			for (Object o : v) {
				if (o != null) {
					try {
						String convertStr = null;
						Number convertNumber = null;
						if (o instanceof Number) {
							convertNumber = (Number) o;
						} else if (o instanceof String) {
							convertStr = (String) o;
						}

						if (i == ExcelConstants._0_CELL) {

							if (convertNumber == null) {
								try {
									Double aux = Double.valueOf(convertStr);
									if (aux == null) {
										continue;
									}
								} catch (Exception e) {
									continue;
								}

							}
							two = new TemplateTwo();
							two.setCodigo(convertStr);
						} else if (i == ExcelConstants._1_CELL) {
							two.setDescricao(convertStr);
						} else if (i == ExcelConstants._2_CELL) {
							two.setQuantidade(convertStr);
						} else if (i == ExcelConstants._3_CELL) {
							two.setMarca(convertStr);
						} else if (i == ExcelConstants._4_CELL) {
							two.setValor(BigDecimal.valueOf(convertNumber.doubleValue()));
						}
					} catch (Exception e) {
					}
					i++;
				}
			}
			i = 0;

			if (two != null) {
				resp.add(two);
			}

		}

		return resp;
	}

	@Override
	public List<Produto> convertProduto(List<TemplateTwo> templates, Fornecedor fornecedor) {

		List<Produto> resp = new ArrayList<>();
		for (TemplateTwo template : templates) {
			Produto produto = new Produto();
			produto.setProdutoId(null);
			produto.setCodigo(template.getCodigo());
			produto.setDescricao(template.getDescricao());
			produto.setUnidade(template.getQuantidade());
			produto.setMarca(template.getMarca());
			produto.setPreco(template.getValor());
			produto.setFornecedor(fornecedor);
			resp.add(produto);
		}

		return resp;
	}
	
}
