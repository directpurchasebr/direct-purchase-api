package br.com.directpurchase.poi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.directpurchase.dto.TemplateOne;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.util.ExcelConstants;

@Service
public class ImportTamplateOne implements ImportTamplate<TemplateOne> {

	@Override
	public List<TemplateOne> convertTemplate(Map<Integer, List<Object>> excel) {

		List<TemplateOne> resp = new ArrayList<>();

		for (Map.Entry<Integer, List<Object>> entry : excel.entrySet()) {
			List<Object> v = entry.getValue();

			int i = 0;
			TemplateOne one = null;
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
							one = new TemplateOne();
							Integer id = convertNumber.intValue();
							one.setCodigo(id.toString());
						} else if (i == ExcelConstants._1_CELL) {
							one.setDescricao(convertStr);
						} else if (i == ExcelConstants._2_CELL) {
							one.setUnidade(convertStr);
						} else if (i == ExcelConstants._3_CELL) {
							one.setValor(BigDecimal.valueOf(convertNumber.doubleValue()));
						}
					} catch (Exception e) {
					}
					i++;
				}
			}
			i = 0;

			if (one != null) {
				resp.add(one);
			}

		}

		return resp;
	}

	@Override
	public List<Produto> convertProduto(List<TemplateOne> templates, Fornecedor fornecedor) {

		List<Produto> resp = new ArrayList<>();
		for (TemplateOne template : templates) {
			Produto produto = new Produto();
			produto.setProdutoId(null);
			produto.setCodigo(template.getCodigo());
			produto.setDescricao(template.getDescricao());
			produto.setUnidade(template.getUnidade());
			produto.setMarca(null);
			produto.setPreco(template.getValor());
			produto.setFornecedor(fornecedor);
			resp.add(produto);
		}

		return resp;
	}

}
