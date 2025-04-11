package br.com.directpurchase.excel.poi;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.directpurchase.dto.TemplateThree;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.util.ExcelConstants;

@Service
public class ImportTamplateThree extends AbstractImportTemplate<TemplateThree> {

	@Override
	protected TemplateThree mapRow(List<Object> row) {
		if (row == null || row.size() < 4)
			return null;

		TemplateThree t = new TemplateThree();

		Number codigo = getNumber(row.get(ExcelConstants._0_CELL));
		String descricao = getString(row.get(ExcelConstants._1_CELL));
		String embalagem = getString(row.get(ExcelConstants._2_CELL));
		String marca = getString(row.get(ExcelConstants._3_CELL));
		String quantidade = getString(row.get(ExcelConstants._4_CELL));
		Number valor = getNumber(row.get(ExcelConstants._5_CELL));
		String caixa = getString(row.get(ExcelConstants._6_CELL));

		if (codigo == null || descricao == null || valor == null)
			return null;

		t.setCodigo(String.valueOf(codigo.intValue()));
		t.setDescricao(descricao);
		t.setEmbalagem(embalagem);
		t.setMarca(marca);
		t.setQuantidade(quantidade);
		t.setValor(BigDecimal.valueOf(valor.doubleValue()));
		t.setCaixa(caixa);

		return t;
	}

	@Override
	public List<Produto> convertProduto(List<TemplateThree> templates, Fornecedor fornecedor) {
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
