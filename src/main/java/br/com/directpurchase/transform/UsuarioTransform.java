package br.com.directpurchase.transform;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.directpurchase.auth.payload.UsuarioPayload;
import br.com.directpurchase.dto.CompradorDto;
import br.com.directpurchase.dto.FornecedorDto;
import br.com.directpurchase.dto.PerfilDto;
import br.com.directpurchase.dto.UsuarioDto;
import br.com.directpurchase.entity.Comprador;
import br.com.directpurchase.entity.Fornecedor;
import br.com.directpurchase.entity.Perfil;
import br.com.directpurchase.entity.Usuario;
import br.com.directpurchase.repository.CompradorRepository;
import br.com.directpurchase.repository.FornecedorRespository;
import br.com.directpurchase.repository.PerfilRepository;
import br.com.directpurchase.repository.UsuarioRepository;
import br.com.directpurchase.util.PasswordUtil;

@Component
public class UsuarioTransform {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private FornecedorRespository fornecedorRespository;

	@Autowired
	private CompradorRepository compradorRepository;

	public UsuarioPayload fetchUsuarioPayload(Integer usuarioId) {

		Usuario entity = usuarioRepository.findById(usuarioId).get();
		UsuarioDto transform = transform(entity);

		List<Integer> compradores = transform.getCompradores().stream()
		        .map(CompradorDto::getCompradorId)
		        .collect(Collectors.toList());

		List<Integer> fornecedores = transform.getFornecedores().stream()
		        .map(FornecedorDto::getFornecedorId)
		        .collect(Collectors.toList());

		return UsuarioPayload.builder()
		        .usuarioId(transform.getUsuarioId())
		        .nome(transform.getNome())
		        .email(transform.getEmail())
		        .login(transform.getLogin())
		        .senha(transform.getSenha())
		        .indEstoque(transform.getIndEstoque())
		        .perfil(transform.getPerfil().getDescricao())
		        .status(null)
		        .fornecedores(fornecedores)
		        .compradores(compradores)
		        .build();
	}

	public UsuarioDto fetchUsuarioDto(Integer usuarioId) {
		Usuario entity = usuarioRepository.findById(usuarioId).get();
		return transform(entity);
	}

	public UsuarioDto transform(Usuario entity) {

		PerfilDto perfil = PerfilDto.builder().perfilId(entity.getPerfil().getPerfilId())
		        .descricao(entity.getPerfil().getDescricao()).build();
		List<CompradorDto> compradores = entity.getCompradores().stream().map(c -> transform(c))
		        .collect(Collectors.toList());
		List<FornecedorDto> fornecedores = entity.getFornecedores().stream().map(f -> transform(f))
		        .collect(Collectors.toList());

		return UsuarioDto.builder()
		        .usuarioId(entity.getUsuarioId())
		        .nome(entity.getNome())
		        .email(entity.getEmail())
		        .login(entity.getLogin())
		        // .senha(entity.getSenha()) // TODO: nao deve enviar a senha para o front
		        .indEstoque(entity.getIndEstoque())
		        .dataNascimento(entity.getDataNascimento().toLocalDate())
		        .perfil(perfil)
		        .fornecedores(fornecedores)
		        .compradores(compradores)
		        .build();

	}

	public Usuario transform(UsuarioDto bean) {

		Usuario entity = null;
		if (bean.getUsuarioId() != null) {
			entity = usuarioRepository.findById(bean.getUsuarioId()).get();
		} else {
			entity = new Usuario();
			entity.setUsuarioId(null);

			String password = bean.getSenha();
			if (password == null) {

				// FIXME: senha padrao de primeiro acesso
				password = "123456";
			}

			final String senhaEnc = PasswordUtil.encryptPassword(password);
			entity.setSenha(senhaEnc);

			entity.setEmail(bean.getEmail());
			entity.setLogin(bean.getLogin());
			entity.setDataCadastro(LocalDateTime.now());
		}

		entity.setNome(bean.getNome());
		entity.setDataNascimento(bean.getDataNascimento() == null ? null : bean.getDataNascimento().atStartOfDay());
		entity.setDataModif(LocalDateTime.now());
		entity.setIndEstoque(bean.getIndEstoque());

		Perfil perfil = getPerfil(bean.getPerfil() != null ? bean.getPerfil().getPerfilId() : null);
		entity.setPerfil(perfil);

		List<Fornecedor> fornecedores = bean.getFornecedores() == null ? null
		        : bean.getFornecedores().stream().map(f -> findFornecedorById(f)).collect(Collectors.toList());
		entity.setFornecedores(fornecedores);

		List<Comprador> compradores = bean.getCompradores() == null ? null
		        : bean.getCompradores().stream().map(c -> findCompradorById(c)).collect(Collectors.toList());
		entity.setCompradores(compradores);

		return entity;
	}

	public FornecedorDto transform(Fornecedor entity) {
		return FornecedorDto.builder()
		        .fornecedorId(entity.getFornecedorId())
		        .codigo(entity.getCodigo())
		        .nome(entity.getNome())
		        .layoutExcel(entity.getLayoutExcel())
		        .build();
	}

	public CompradorDto transform(Comprador entity) {
		return CompradorDto.builder()
		        .compradorId(entity.getCompradorId())
		        .codigo(entity.getCodigo())
		        .nome(entity.getNome())
		        .negocioId(entity.getNegocio().getNegocioId())
		        .build();
	}

	public PerfilDto transform(Perfil entity) {
		return PerfilDto.builder()
		        .perfilId(entity.getPerfilId())
		        .descricao(entity.getDescricao())
		        .build();
	}

	private Perfil getPerfil(Integer perfilId) {
		if (perfilId == null) {
			List<Perfil> list = perfilRepository.buscaRegular();
			return list.stream().findFirst().get();
		} else {
			Optional<Perfil> perfil = perfilRepository.findById(perfilId);
			return perfil.get();
		}
	}

	private Fornecedor findFornecedorById(FornecedorDto bean) {
		Optional<Fornecedor> optional = fornecedorRespository.findById(bean.getFornecedorId());
		return optional.get();
	}

	private Comprador findCompradorById(CompradorDto bean) {
		Optional<Comprador> optional = compradorRepository.findById(bean.getCompradorId());
		return optional.get();
	}

}
