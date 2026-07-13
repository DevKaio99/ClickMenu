package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.mappers.UsuarioMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class CriarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public CriarUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponseDTO executar(UsuarioCreateDTO usuarioCreateDTO) {
        if (usuarioRepository.validarEmailExistente(usuarioCreateDTO.email())) {
            throw new BusinessException("Email já cadastrado.");
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioCreateDTO);
        usuario.setSenha(passwordEncoder.encode(usuarioCreateDTO.senha()));
        usuario.setDataUltimaAlteracao(LocalDateTime.now());
        Usuario usuarioCriado = usuarioRepository.salvarUsuario(usuario);

        return usuarioMapper.usuarioResponseDTO(usuarioCriado);

    }


}

