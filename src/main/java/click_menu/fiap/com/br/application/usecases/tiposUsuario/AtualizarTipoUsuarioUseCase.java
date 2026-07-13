package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioUpdateDTO;
import click_menu.fiap.com.br.infrastructure.mappers.TipoUsuarioMapper;

import java.util.UUID;

public class AtualizarTipoUsuarioUseCase {
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final TipoUsuarioMapper tipoUsuarioMapper;

    public AtualizarTipoUsuarioUseCase(TipoUsuarioRepository tipoUsuarioRepository, TipoUsuarioMapper tipoUsuarioMapper) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.tipoUsuarioMapper = tipoUsuarioMapper;
    }

    public TipoUsuarioResponseDTO executar(UUID id, TipoUsuarioUpdateDTO tipoUsuarioUpdateDTO) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.buscarTipoUsuarioPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado"));

        boolean nomeAlterado = !tipoUsuario.getNomeTipo().equals(tipoUsuarioUpdateDTO.nomeTipo());

        if (nomeAlterado && tipoUsuarioRepository.validarNomeTipoExistente(tipoUsuarioUpdateDTO.nomeTipo())) {
            throw new BusinessException("Já existe um tipo de usuário cadastrado com esse nome");
        }

        tipoUsuario.setNomeTipo(tipoUsuarioUpdateDTO.nomeTipo());

        TipoUsuario tipoUsuarioAtualizado = tipoUsuarioRepository.atualizarTipoUsuario(tipoUsuario);

        return tipoUsuarioMapper.tipoUsuarioResponseDTO(tipoUsuarioAtualizado);
    }
}
