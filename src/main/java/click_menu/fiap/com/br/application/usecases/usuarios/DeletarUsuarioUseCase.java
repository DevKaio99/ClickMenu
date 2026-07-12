package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;

import java.util.UUID;

public class DeletarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final RestauranteRepository restauranteRepository;

    public DeletarUsuarioUseCase(UsuarioRepository usuarioRepository, RestauranteRepository restauranteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public void executar (UUID id) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorId(id).
                orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (restauranteRepository.existePorUsuarioId(id)) {
            throw new BusinessException("Não é possivel excluir um usuário que possui restaurantes cadastrados");
        }

        usuarioRepository.deletarUsuario(id);
    }
}
