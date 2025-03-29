package br.com.directpurchase.excel.poi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.directpurchase.dto.TemplateThree;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.util.ExcelConstants;

@Service
public class ImportTamplateThree implements ImportTamplate<TemplateThree> {

	@Override
	public List<TemplateThree> convertTemplate(Map<Integer, List<Object>> excel) {

		List<TemplateThree> resp = new ArrayList<>();

		for (Map.Entry<Integer, List<Object>> entry : excel.entrySet()) {
			List<Object> v = entry.getValue();

			int i = 0;
			TemplateThree three = null;
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
								continue;
							}
							three = new TemplateThree();
							if (convertStr != null) {
								three.setCodigo(convertStr);
							} else {
								Integer id = convertNumber.intValue();
								three.setCodigo(id.toString());
							}
						} else if (i == ExcelConstants._1_CELL) {
							three.setDescricao(convertStr);
						} else if (i == ExcelConstants._2_CELL) {
							three.setEmbalagem(convertStr);
						} else if (i == ExcelConstants._3_CELL) {
							three.setMarca(convertStr);
						} else if (i == ExcelConstants._4_CELL) {
							three.setQuantidade(convertStr);
						} else if (i == ExcelConstants._5_CELL) {
							three.setValor(BigDecimal.valueOf(convertNumber.doubleValue()));
						} else if (i == ExcelConstants._6_CELL) {
							three.setCaixa(convertStr);
						}
					} catch (Exception e) {
					}
					i++;
				}
			}
			i = 0;

			if (three != null) {
				resp.add(three);
			}

		}

		return resp;
	}
	
	@Override
	public List<Produto> convertProduto(List<TemplateThree> templates, Fornecedor fornecedor) {

		List<Produto> resp = new ArrayList<>();
		for (TemplateThree template : templates) {
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
