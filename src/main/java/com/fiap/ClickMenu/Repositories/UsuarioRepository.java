package com.fiap.ClickMenu.Repositories;

import com.fiap.ClickMenu.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    Optional<Usuario> findById(Long id);
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

}
