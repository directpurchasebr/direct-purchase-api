package br.com.directpurchase.excel.poi;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.directpurchase.dto.TemplateOne;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.util.ExcelConstants;

@Component
public class ImportTamplateOne extends AbstractImportTemplate<TemplateOne> {

	@Override
	protected TemplateOne mapRow(List<Object> row) {
		if (row == null || row.size() < 4)
			return null;

		TemplateOne t = new TemplateOne();

		Number codigo = getNumber(row.get(ExcelConstants._0_CELL));
		String descricao = getString(row.get(ExcelConstants._1_CELL));
		String unidade = getString(row.get(ExcelConstants._2_CELL));
		Number valor = getNumber(row.get(ExcelConstants._3_CELL));

		if (codigo == null || descricao == null || unidade == null || valor == null)
			return null;

		t.setCodigo(String.valueOf(codigo.intValue()));
		t.setDescricao(descricao);
		t.setUnidade(unidade);
		t.setValor(BigDecimal.valueOf(valor.doubleValue()));

		return t;
	}

	@Override
	public List<Produto> convertProduto(List<TemplateOne> templates, Usuario usuario, Fornecedor fornecedor) {
		return templates.stream().map(t -> Produto.builder()
				.produtoId(null)
				.codigo(t.getCodigo())
				.descricao(t.getDescricao())
				.unidade(t.getUnidade())
				.marca(null)
				.preco(t.getValor())
				.usuario(usuario)
				.fornecedor(fornecedor)
				.build()).collect(Collectors.toList());
	}
}
