package com.fiap.ClickMenu.Repositories;

import com.fiap.ClickMenu.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmailIgnoreCase(String email);
}
