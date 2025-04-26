package br.com.directpurchase.excel.service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dto.TemplateFour;
import br.com.directpurchase.dto.TemplateOne;
import br.com.directpurchase.dto.TemplateThree;
import br.com.directpurchase.dto.TemplateTwo;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Produto;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.excel.poi.ImportTamplateFour;
import br.com.directpurchase.excel.poi.ImportTamplateOne;
import br.com.directpurchase.excel.poi.ImportTamplateThree;
import br.com.directpurchase.excel.poi.ImportTamplateTwo;
import br.com.directpurchase.excel.poi.ImportaProdutosExcel;
import br.com.directpurchase.excel.poi.Template;
import br.com.directpurchase.exception.ValidationException;
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
	private AuthUtils authUtils;

	public Status importaExcel(Integer fornecedorId, InputStream file) throws ValidationException {

		UsuarioPayload usuarioLogado = authUtils.getUsuarioLogado();
		if (usuarioLogado == null) {
			throw new ValidationException("Usuário não está logado!");
		}

		Usuario usuario = new Usuario(usuarioLogado.getUsuarioId());
		Fornecedor fornecedor = new Fornecedor(fornecedorId);
		// Fornecedor fornecedor = fornecedorRespository.findById(fornecedorId).get();

		List<Produto> auxList = new ArrayList<>();
		try {
			Map<Integer, List<Object>> imports = importaProdutosExcel.readExcelFile(file);
			Template template = validateTemplate.validate(imports);

			if (template.equals(Template.ONE)) {
				List<TemplateOne> ones = importTamplateOne.convertTemplate(imports);
				auxList.addAll(importTamplateOne.convertProduto(ones, usuario, fornecedor));
			}

			if (template.equals(Template.TWO)) {
				List<TemplateTwo> twos = importTamplateTwo.convertTemplate(imports);
				auxList.addAll(importTamplateTwo.convertProduto(twos, usuario, fornecedor));
			}
			if (template.equals(Template.THREE)) {
				List<TemplateThree> threes = importTamplateThree.convertTemplate(imports);
				auxList.addAll(importTamplateThree.convertProduto(threes, usuario, fornecedor));
			}

			if (template.equals(Template.FOUR)) {
				List<TemplateFour> fours = importTamplateFour.convertTemplate(imports);
				auxList.addAll(importTamplateFour.convertProduto(fours, usuario, fornecedor));
			}

			List<Produto> produtos = auxList.stream().map(p -> validaProdutoJaExiste(p)).collect(Collectors.toList());
			produtoRepository.saveAll(produtos);
		} catch (Exception e) {
			log.error("", e);
			return new Status(Boolean.FALSE, "", "ERRO ao importar EXCEL", null);
		}

		return new Status(Boolean.TRUE, "Importação feita com sucesso", "", null);
	}

	private Produto validaProdutoJaExiste(Produto newImport) {
		List<Produto> list = produtoRepository.buscaProdutoMain(
				newImport.getUsuario().getUsuarioId(),
				newImport.getCodigo(),
				newImport.getFornecedor().getFornecedorId());

		if (list != null && !list.isEmpty()) {
			// FiXME: deve retornar apenas um item da lista
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
