package br.com.directpurchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.directpurchase.entity.UsuarioSession;

public interface UsuarioSessionRepository extends JpaRepository<UsuarioSession, Integer> {

    @Query("select us from UsuarioSession us where us.tokenAccess = :tokenAccess")
    public List<UsuarioSession> findByTokenAccess(@Param("tokenAccess") String tokenAccess);

}
