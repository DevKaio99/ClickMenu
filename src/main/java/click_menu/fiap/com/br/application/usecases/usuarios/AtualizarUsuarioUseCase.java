package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioUpdateDTO;
import click_menu.fiap.com.br.infrastructure.mappers.UsuarioMapper;

import java.time.LocalDateTime;
import java.util.UUID;

public class AtualizarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;


    public AtualizarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponseDTO executar(UUID id, UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorId(id).
                orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));

        if (!usuario.getEmail().equals(usuarioUpdateDTO.email())
                && usuarioRepository.validarEmailExistente(usuarioUpdateDTO.email())) {
            throw new BusinessException("Email já cadastrado.");
        }

        usuario.setNome(usuarioUpdateDTO.nome());
        usuario.setEmail(usuarioUpdateDTO.email());
        usuario.setTipo(usuarioUpdateDTO.tipo());
        usuario.setDataUltimaAlteracao(LocalDateTime.now());

        Usuario usuarioAtualizado = usuarioRepository.atualizarUsuario(usuario);

        return usuarioMapper.usuarioResponseDTO(usuarioAtualizado);

    }
}
