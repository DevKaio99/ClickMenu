package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.UsuarioMapper;

import java.util.List;

public class ListarUsuariosUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public ListarUsuariosUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public List<UsuarioResponseDTO> executar() {
        return usuarioRepository.listarUsuarios()
                .stream()
                .map(usuarioMapper::usuarioResponseDTO)
                .toList();
    }
}
