package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;

import java.util.UUID;

public class DeletarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public DeletarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void executar (UUID id) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorId(id).
                orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        usuarioRepository.deletarUsuario(id);
    }
}
