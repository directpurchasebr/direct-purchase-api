package br.com.directpurchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.directpurchase.dao.UsuarioDao;
import br.com.directpurchase.dto.UsuarioDto;
import br.com.directpurchase.util.PasswordUtil;

@Service
public class LoginService {

	@Autowired
	private UsuarioDao usuarioDao;

	public UsuarioDto logar(final String login, final String senha) {

		final String senhaEnc = PasswordUtil.encryptPassword(senha);
		UsuarioDto retorno = usuarioDao.findByUsuarioLoginSenha(login, senhaEnc);

		if (retorno != null) {
			retorno.setStatus("SUCCESS");
		}
		return retorno;
	}
}
