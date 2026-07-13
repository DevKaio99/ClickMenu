package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.TipoUsuarioMapper;

import java.util.UUID;

public class BuscarTipoUsuarioPorIdUseCase {
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final TipoUsuarioMapper tipoUsuarioMapper;

    public BuscarTipoUsuarioPorIdUseCase(TipoUsuarioRepository tipoUsuarioRepository, TipoUsuarioMapper tipoUsuarioMapper) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.tipoUsuarioMapper = tipoUsuarioMapper;
    }

    public TipoUsuarioResponseDTO executar(UUID id) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.buscarTipoUsuarioPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado"));

        return tipoUsuarioMapper.tipoUsuarioResponseDTO(tipoUsuario);
    }
}
