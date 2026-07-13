package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioUpdateDTO;
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
    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    private AtualizarUsuarioUseCase atualizarUsuarioUseCase;


    @BeforeEach
    void setUp() {
        atualizarUsuarioUseCase = new AtualizarUsuarioUseCase(usuarioRepository, usuarioMapper, tipoUsuarioRepository);
    }

    @Test
    void devePermitirAtualizarUsuarioQuandoEmailSeMantem() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");

        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                tipoCliente);

        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO(
                "Teste",
                "teste@email.com",
                tipoCliente.getId());

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                usuario.getId(),
                "Teste",
                "teste@email.com",
                LocalDateTime.now(),
                new TipoUsuarioResponseDTO(tipoCliente.getId(), "CLIENTE"));

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));
        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(tipoCliente.getId())).thenReturn(Optional.of(tipoCliente));
        when(usuarioRepository.atualizarUsuario(usuario)).thenReturn(usuario);
        when(usuarioMapper.usuarioResponseDTO(usuario)).thenReturn(usuarioResponseDTO);

        atualizarUsuarioUseCase.executar(id, usuarioUpdateDTO);

        verify(usuarioRepository).atualizarUsuario(usuario);

    }

    @Test
    void devePermitirAtualizarUsuarioQuandoNovoEmailEstaDisponivel() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");

        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                tipoCliente);

        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO(
                "Teste",
                "novoteste@email.com",
                tipoCliente.getId());

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                usuario.getId(),
                "Teste",
                "teste@email.com",
                LocalDateTime.now(),
                new TipoUsuarioResponseDTO(tipoCliente.getId(), "CLIENTE"));

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.validarEmailExistente("novoteste@email.com")).thenReturn(false);
        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(tipoCliente.getId())).thenReturn(Optional.of(tipoCliente));
        when(usuarioRepository.atualizarUsuario(usuario)).thenReturn(usuario);
        when(usuarioMapper.usuarioResponseDTO(usuario)).thenReturn(usuarioResponseDTO);

        atualizarUsuarioUseCase.executar(id, usuarioUpdateDTO);

        verify(usuarioRepository).atualizarUsuario(usuario);
    }

    @Test
    void naoDevePermitirAtualizarUsuarioQuandoUsuarioNaoExiste() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");

        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO(
                "Teste",
                "novoteste@email.com",
                tipoCliente.getId());

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atualizarUsuarioUseCase.executar(id, usuarioUpdateDTO));

        verify(usuarioRepository, never()).atualizarUsuario(any());
    }

    @Test
    void naoDevePermitirAtualizarUsuarioQuandoEmailMudouEExisteNovoNoBanco() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");

        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                tipoCliente);

        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO(
                "Teste",
                "novoteste@email.com",
                tipoCliente.getId());


        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.validarEmailExistente("novoteste@email.com")).thenReturn(true);

        assertThrows(BusinessException.class, () -> atualizarUsuarioUseCase.executar(id, usuarioUpdateDTO));

        verify(usuarioRepository, never()).atualizarUsuario(usuario);
    }

    @Test
    void naoDevePermitirAtualizarUsuarioQuandoTipoNaoEncontrado() {
        UUID id = UUID.randomUUID();
        UUID novoTipoId = UUID.randomUUID();
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");

        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                tipoCliente);

        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO(
                "Teste",
                "teste@email.com",
                novoTipoId);

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));
        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(novoTipoId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atualizarUsuarioUseCase.executar(id, usuarioUpdateDTO));

        verify(usuarioRepository, never()).atualizarUsuario(any());
    }
}
