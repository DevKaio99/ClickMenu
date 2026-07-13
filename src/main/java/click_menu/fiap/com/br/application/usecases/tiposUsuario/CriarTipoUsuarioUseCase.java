package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.TipoUsuarioMapper;

public class CriarTipoUsuarioUseCase {
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final TipoUsuarioMapper tipoUsuarioMapper;

    public CriarTipoUsuarioUseCase(TipoUsuarioRepository tipoUsuarioRepository, TipoUsuarioMapper tipoUsuarioMapper) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.tipoUsuarioMapper = tipoUsuarioMapper;
    }

    public TipoUsuarioResponseDTO executar(TipoUsuarioCreateDTO tipoUsuarioCreateDTO) {
        if (tipoUsuarioRepository.validarNomeTipoExistente(tipoUsuarioCreateDTO.nomeTipo())) {
            throw new BusinessException("Já existe um tipo de usuário cadastrado com esse nome");
        }

        TipoUsuario tipoUsuario = tipoUsuarioMapper.toEntity(tipoUsuarioCreateDTO);
        TipoUsuario tipoUsuarioCriado = tipoUsuarioRepository.salvarTipoUsuario(tipoUsuario);

        return tipoUsuarioMapper.tipoUsuarioResponseDTO(tipoUsuarioCriado);
    }
}
