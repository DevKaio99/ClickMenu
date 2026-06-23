package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.infrastructure.dtos.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.UsuarioResponseDTO;
import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.mapper.UsuarioMapper;

import java.time.LocalDateTime;

public class CriarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public CriarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponseDTO executar(UsuarioCreateDTO usuarioCreateDTO) {
        if (usuarioRepository.validarEmailExistente(usuarioCreateDTO.email())) {
            throw new BusinessException("Email já cadastrado.");
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioCreateDTO);
        usuario.setDataUltimaAlteracao(LocalDateTime.now());
        Usuario usuarioCriado = usuarioRepository.salvarUsuario(usuario);

        return usuarioMapper.usuarioResponseDTO(usuarioCriado);

    }


}

