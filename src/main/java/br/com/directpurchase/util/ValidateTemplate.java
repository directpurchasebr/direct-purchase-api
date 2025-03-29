package br.com.directpurchase.util;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.directpurchase.excel.poi.Template;

@Component
public class ValidateTemplate {

	boolean one1 = Boolean.FALSE;
	boolean one2 = Boolean.FALSE;
	boolean one3 = Boolean.FALSE;
	boolean one4 = Boolean.FALSE;

	boolean two1 = Boolean.FALSE;
	boolean two2 = Boolean.FALSE;
	boolean two3 = Boolean.FALSE;
	boolean two4 = Boolean.FALSE;
	boolean two5 = Boolean.FALSE;

	boolean three1 = Boolean.FALSE;
	boolean three2 = Boolean.FALSE;
	boolean three3 = Boolean.FALSE;
	boolean three4 = Boolean.FALSE;
	boolean three5 = Boolean.FALSE;
	boolean three6 = Boolean.FALSE;
	boolean three7 = Boolean.FALSE;

	boolean four1 = Boolean.FALSE;
	boolean four2 = Boolean.FALSE;
	boolean four3 = Boolean.FALSE;
	boolean four4 = Boolean.FALSE;

	public Template validate(Map<Integer, List<Object>> excel) {

		for (Map.Entry<Integer, List<Object>> entry : excel.entrySet()) {
			Integer key = entry.getKey();
			List<Object> v = entry.getValue();

			for (Object o : v) {
				try {
					if (key == ExcelConstants._0_ROW) {

						String convertStr = (String) o;

						Template template = validaOne(convertStr);
						if (template == null) {
							template = validaTwo(convertStr);
						}
						if (template == null) {
							template = validaThree(convertStr);
						}
						if (template == null) {
							template = validaFour(convertStr);
						}

						if (template != null) {
							return template;
						}
					}
					if (key != 0) {
						break;
					}
				} catch (Exception e) {
					return null;
				}
			}
			if (key != 0) {
				break;
			}
		}

		return null;
	}

	private Template validaOne(String convertStr) {
		if (convertStr.trim().equals("PRODUTO")) {
			one1 = Boolean.TRUE;
		}
		if (one1 && convertStr.trim().equals("DESCRICAO")) {
			one2 = Boolean.TRUE;
		}
		if (one1 && one2 && convertStr.trim().equals("UNIDADE")) {
			one3 = Boolean.TRUE;
		}
		if (one1 && one2 && one3 && convertStr.trim().equals("PROMOCAO_2")) {
			one4 = Boolean.TRUE;
		}
		if (one4) {
			return Template.ONE;
		}
		return null;
	}

	private Template validaTwo(String convertStr) {
		if (convertStr.trim().equals("Código")) {
			two1 = Boolean.TRUE;
		}
		if (two1 && convertStr.trim().equals("Descrição Mercadoria")) {
			two2 = Boolean.TRUE;
		}
		if (two1 && two2 && convertStr.trim().equals("Quant. Embal.")) {
			two3 = Boolean.TRUE;
		}
		if (two1 && two2 && two3 && convertStr.trim().equals("Marca")) {
			two4 = Boolean.TRUE;
		}
		if (two1 && two2 && two3 && two4 && convertStr.trim().equals("P. Indiv.")) {
			two5 = Boolean.TRUE;
		}
		if (two5) {
			return Template.TWO;
		}

		return null;
	}

	private Template validaThree(String convertStr) {
		if (convertStr.trim().equals("CODIGO")) {
			three1 = Boolean.TRUE;
		}
		if (three1 && convertStr.trim().equals("DESCRIÇÃO")) {
			three2 = Boolean.TRUE;
		}
		if (three1 && three2 && convertStr.trim().equals("EMBALAGEM")) {
			three3 = Boolean.TRUE;
		}
		if (three1 && three2 && three3 && convertStr.trim().equals("MARCA")) {
			three4 = Boolean.TRUE;
		}
		if (three1 && three2 && three3 && three4 && convertStr.trim().equals("Q.VENDAS")) {
			three5 = Boolean.TRUE;
		}
		if (three1 && three2 && three3 && three4 && three5 && convertStr.trim().equals("PRECO")) {
			three6 = Boolean.TRUE;
		}
		if (three1 && three2 && three3 && three4 && three5 && three6 && convertStr.trim().equals("CX FECHADA")) {
			three7 = Boolean.TRUE;
		}
		if (three7) {
			return Template.THREE;
		}

		return null;
	}

	private Template validaFour(String convertStr) {
		if (convertStr.trim().equals("Codigo")) {
			four1 = Boolean.TRUE;
		}
		if (four1 && convertStr.trim().equals("Descrição")) {
			four2 = Boolean.TRUE;
		}
		if (four1 && four2 && convertStr.trim().equals("Unidade")) {
			four3 = Boolean.TRUE;
		}
		if (four1 && four2 && four3 && convertStr.trim().equals("Pr.Venda")) {
			four4 = Boolean.TRUE;
		}
		if (four4) {
			return Template.FOUR;
		}
		return null;
	}
}