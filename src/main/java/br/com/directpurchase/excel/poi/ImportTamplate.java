package br.com.directpurchase.excel.poi;

import java.util.List;
import java.util.Map;

import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.entity.Usuario;

public interface ImportTamplate<T> {

	public List<T> convertTemplate(Map<Integer, List<Object>> excel);

	public List<Produto> convertProduto(List<T> templates, Usuario usuario, Fornecedor fornecedor);
}
