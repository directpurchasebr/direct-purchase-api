package br.com.directpurchase.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.directpurchase.entity.Usuario;

public interface UsuarioRepository  extends CrudRepository<Usuario, Integer> {

}
