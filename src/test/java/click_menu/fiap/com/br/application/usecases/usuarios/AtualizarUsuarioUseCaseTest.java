package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.UsuarioUpdateDTO;
import click_menu.fiap.com.br.infrastructure.mappers.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtualizarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioMapper usuarioMapper;

    private AtualizarUsuarioUseCase atualizarUsuarioUseCase;


    @BeforeEach
    void setUp() {
        atualizarUsuarioUseCase = new AtualizarUsuarioUseCase(usuarioRepository, usuarioMapper);
    }

    @Test
    void devePermitirAtualizarUsuarioQuandoEmailSeMantem() {
        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.CLIENTE);

        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO(
                "Teste",
                "teste@email.com",
                TipoUsuario.CLIENTE);

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                usuario.getId(),
                "Teste",
                "teste@email.com",
                LocalDateTime.now(),
                TipoUsuario.CLIENTE);

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.usuarioResponseDTO(usuario)).thenReturn(usuarioResponseDTO);

        atualizarUsuarioUseCase.executar(id, usuarioUpdateDTO);

        verify(usuarioRepository).atualizarUsuario(usuario);

    }

    @Test
    void devePermitirAtualizarUsuarioQuandoNovoEmailEstaDisponivel() {
        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.CLIENTE);

        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO(
                "Teste",
                "novoteste@email.com",
                TipoUsuario.CLIENTE);

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                usuario.getId(),
                "Teste",
                "teste@email.com",
                LocalDateTime.now(),
                TipoUsuario.CLIENTE);

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.validarEmailExistente("novoteste@email.com")).thenReturn(false);
        when(usuarioMapper.usuarioResponseDTO(usuario)).thenReturn(usuarioResponseDTO);

        atualizarUsuarioUseCase.executar(id, usuarioUpdateDTO);

        verify(usuarioRepository).atualizarUsuario(usuario);
    }

    @Test
    void naoDevePermitirAtualizarUsuarioQuandoUsuarioNaoExiste() {
        UUID id = UUID.randomUUID();
        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO(
                "Teste",
                "novoteste@email.com",
                TipoUsuario.CLIENTE);

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atualizarUsuarioUseCase.executar(id, usuarioUpdateDTO));

        verify(usuarioRepository, never()).atualizarUsuario(any());
    }

    @Test
    void naoDevePermitirAtualizarUsuarioQuandoEmailMudouEExisteNovoNoBanco() {
        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.CLIENTE);

        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO(
                "Teste",
                "novoteste@email.com",
                TipoUsuario.CLIENTE);


        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.validarEmailExistente("novoteste@email.com")).thenReturn(true);

        assertThrows(BusinessException.class, () -> atualizarUsuarioUseCase.executar(id, usuarioUpdateDTO));

        verify(usuarioRepository, never()).atualizarUsuario(usuario);
    }
}
