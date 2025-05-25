package br.com.directpurchase.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.directpurchase.auth.dao.AuthDao;
import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.auth.utils.AuthUtils;
import br.com.directpurchase.dao.EntitysFetchDao;
import br.com.directpurchase.dao.PessoaDao;
import br.com.directpurchase.dto.CompradorDto;
import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.repository.CompradorRepository;
import br.com.directpurchase.response.Status;
import br.com.directpurchase.transform.PessoaTransform;

@Service
public class CompradorService {

	private final PessoaTransform pessoaTransform;
	private final AuthUtils authUtils;
	private final CompradorRepository compradorRepository;
	private final PessoaDao pessoaDao;
	private final EntitysFetchDao entitysFetchDao;
	private final AuthDao authDao;

	public CompradorService(PessoaTransform pessoaTransform, AuthUtils authUtils,
			CompradorRepository compradorRepository, PessoaDao pessoaDao, EntitysFetchDao entitysFetchDao,
			AuthDao authDao) {
		this.pessoaTransform = pessoaTransform;
		this.authUtils = authUtils;
		this.compradorRepository = compradorRepository;
		this.pessoaDao = pessoaDao;
		this.entitysFetchDao = entitysFetchDao;
		this.authDao = authDao;
	}

	public List<CompradorDto> listarCompradores() throws ValidationException {
		UsuarioPayload usuario = authUtils.getUsuarioLogado();
		List<Comprador> compradores = compradorRepository.buscaPorUsuario(usuario.getUsuarioId());
		return compradores.stream().map(pessoaTransform::transform).toList();
	}

	public Status salvarComprador(CompradorDto request) {
		Comprador entity = pessoaTransform.transform(request);
		pessoaDao.salvar(entity.getPessoa());
		pessoaDao.salvar(entity);

		UsuarioPayload usuarioPayload = authUtils.getUsuarioLogado();
		Usuario usuario = entitysFetchDao.findUsuarioById(usuarioPayload.getUsuarioId());
		usuario.getCompradores().add(entity);

		authDao.salvar(usuario);

		CompradorDto responseDto = pessoaTransform.transform(entity);
		return new Status(true, "Comprador cadastrado com sucesso", "", responseDto);
	}
}
