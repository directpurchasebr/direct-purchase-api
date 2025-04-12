package br.com.directpurchase.excel.poi;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.directpurchase.dto.TemplateTwo;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.util.ExcelConstants;

@Service
public class ImportTamplateTwo extends AbstractImportTemplate<TemplateTwo> {

	@Override
	protected TemplateTwo mapRow(List<Object> row) {
		if (row == null || row.size() < 5)
			return null;

		TemplateTwo t = new TemplateTwo();

		Number codigo = getNumber(row.get(ExcelConstants._0_CELL));
		String descricao = getString(row.get(ExcelConstants._1_CELL));
		String unidade = getString(row.get(ExcelConstants._2_CELL));
		String marca = getString(row.get(ExcelConstants._3_CELL));
		Number valor = getNumber(row.get(ExcelConstants._4_CELL));

		if (codigo == null || descricao == null || unidade == null || valor == null)
			return null;

		t.setCodigo(String.valueOf(codigo.intValue()));
		t.setDescricao(descricao);
		t.setQuantidade(unidade);
		t.setMarca(marca);
		t.setValor(BigDecimal.valueOf(valor.doubleValue()));

		return t;
	}

	@Override
	public List<Produto> convertProduto(List<TemplateTwo> templates, Fornecedor fornecedor) {
		return templates.stream().map(t -> Produto.builder()
				.produtoId(null)
				.codigo(t.getCodigo())
				.descricao(t.getDescricao())
				.unidade(t.getQuantidade())
				.marca(t.getMarca())
				.preco(t.getValor())
				.fornecedor(fornecedor)
				.build()).collect(Collectors.toList());
	}

}
