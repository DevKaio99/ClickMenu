package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;

import java.util.UUID;

public class DeletarTipoUsuarioUseCase {
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public DeletarTipoUsuarioUseCase(TipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    public void executar(UUID id) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.buscarTipoUsuarioPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado"));

        tipoUsuarioRepository.deletarTipoUsuario(id);
    }
}
