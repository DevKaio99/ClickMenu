package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.TipoUsuarioMapper;

import java.util.List;

public class ListarTiposUsuarioUseCase {
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final TipoUsuarioMapper tipoUsuarioMapper;

    public ListarTiposUsuarioUseCase(TipoUsuarioRepository tipoUsuarioRepository, TipoUsuarioMapper tipoUsuarioMapper) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.tipoUsuarioMapper = tipoUsuarioMapper;
    }

    public List<TipoUsuarioResponseDTO> executar() {
        return tipoUsuarioRepository.listarTiposUsuario()
                .stream()
                .map(tipoUsuarioMapper::tipoUsuarioResponseDTO)
                .toList();
    }
}
