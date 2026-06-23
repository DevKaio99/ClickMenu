package click_menu.fiap.com.br.domain.repositories;

import click_menu.fiap.com.br.domain.entities.Usuario;


import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    Usuario salvarUsuario (Usuario usuario);
    void deletarUsuario (UUID id);
    boolean validarEmailExistente (String email);
    Optional <Usuario> buscarUsuarioPorId (UUID id);
    Usuario atualizarUsuario(Usuario usuario);
}
