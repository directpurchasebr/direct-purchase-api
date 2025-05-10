package br.com.directpurchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.directpurchase.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
