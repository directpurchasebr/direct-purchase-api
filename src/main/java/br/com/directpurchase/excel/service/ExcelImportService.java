package br.com.directpurchase.excel.service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.dto.TemplateFour;
import br.com.directpurchase.dto.TemplateOne;
import br.com.directpurchase.dto.TemplateThree;
import br.com.directpurchase.dto.TemplateTwo;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.excel.poi.ImportTamplateFour;
import br.com.directpurchase.excel.poi.ImportTamplateOne;
import br.com.directpurchase.excel.poi.ImportTamplateThree;
import br.com.directpurchase.excel.poi.ImportTamplateTwo;
import br.com.directpurchase.excel.poi.ImportaProdutosExcel;
import br.com.directpurchase.excel.poi.Template;
import br.com.directpurchase.repository.FornecedorRespository;
import br.com.directpurchase.repository.ProdutoRepository;
import br.com.directpurchase.response.Status;
import br.com.directpurchase.util.ValidateTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExcelImportService {

	@Autowired
	private ImportaProdutosExcel importaProdutosExcel;

	@Autowired
	private ImportTamplateOne importTamplateOne;

	@Autowired
	private ImportTamplateTwo importTamplateTwo;

	@Autowired
	private ImportTamplateThree importTamplateThree;

	@Autowired
	private ImportTamplateFour importTamplateFour;

	@Autowired
	private ValidateTemplate validateTemplate;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FornecedorRespository fornecedorRespository;

	public Status importaExcel(Integer fornecedorId, InputStream file) {

		Fornecedor fornecedor = fornecedorRespository.findById(fornecedorId).get();

		List<Produto> auxList = new ArrayList<>();
		try {
			Map<Integer, List<Object>> imports = importaProdutosExcel.readExcelFile(file);
			Template template = validateTemplate.validate(imports);

			if (template.equals(Template.ONE)) {
				List<TemplateOne> ones = importTamplateOne.convertTemplate(imports);
				auxList.addAll(importTamplateOne.convertProduto(ones, fornecedor));
			}

			if (template.equals(Template.TWO)) {
				List<TemplateTwo> twos = importTamplateTwo.convertTemplate(imports);
				auxList.addAll(importTamplateTwo.convertProduto(twos, fornecedor));
			}
			if (template.equals(Template.THREE)) {
				List<TemplateThree> threes = importTamplateThree.convertTemplate(imports);
				auxList.addAll(importTamplateThree.convertProduto(threes, fornecedor));
			}

			if (template.equals(Template.FOUR)) {
				List<TemplateFour> fours = importTamplateFour.convertTemplate(imports);
				auxList.addAll(importTamplateFour.convertProduto(fours, fornecedor));
			}

			List<Produto> produtos = auxList.stream().map(p -> validaProduto(p)).collect(Collectors.toList());
			produtoRepository.saveAll(produtos);
		} catch (Exception e) {
			log.error("", e);
			return new Status(Boolean.FALSE, "", "ERRO ao importar EXCEL", null);
		}

		return new Status(Boolean.TRUE, "Importação feita com sucesso", "", null);
	}

	private Produto validaProduto(Produto newImport) {

		List<Produto> list = produtoRepository.buscaProdutoMain(newImport.getCodigo(),
				newImport.getFornecedor().getFornecedorId());
		if (list != null && !list.isEmpty()) {
			Produto old = list.stream().findFirst().get();
			old.setDataModif(LocalDateTime.now());
			old.setDescricao(newImport.getDescricao());
			old.setPreco(newImport.getPreco());
			return old;
		} else {

			newImport.setDataModif(LocalDateTime.now());
			return newImport;
		}

	}

}
