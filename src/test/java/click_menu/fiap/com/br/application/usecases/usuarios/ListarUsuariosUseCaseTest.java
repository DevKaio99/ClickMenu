package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarUsuariosUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioMapper usuarioMapper;

    private ListarUsuariosUseCase listarUsuariosUseCase;

    @BeforeEach
    void setUp() {
        listarUsuariosUseCase = new ListarUsuariosUseCase(usuarioRepository, usuarioMapper);
    }

    @Test
    void deveListarTodosOsUsuariosCadastrados() {
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");
        Usuario usuarioA = new Usuario("UsuarioA", "usuarioa@email.com", "123456", LocalDateTime.now(), tipoCliente);
        Usuario usuarioB = new Usuario("UsuarioB", "usuariob@email.com", "123456", LocalDateTime.now(), tipoCliente);

        UsuarioResponseDTO responseA = new UsuarioResponseDTO(
                usuarioA.getId(), "UsuarioA", "usuarioa@email.com", LocalDateTime.now(),
                new TipoUsuarioResponseDTO(tipoCliente.getId(), "CLIENTE"));
        UsuarioResponseDTO responseB = new UsuarioResponseDTO(
                usuarioB.getId(), "UsuarioB", "usuariob@email.com", LocalDateTime.now(),
                new TipoUsuarioResponseDTO(tipoCliente.getId(), "CLIENTE"));

        when(usuarioRepository.listarUsuarios()).thenReturn(List.of(usuarioA, usuarioB));
        when(usuarioMapper.usuarioResponseDTO(usuarioA)).thenReturn(responseA);
        when(usuarioMapper.usuarioResponseDTO(usuarioB)).thenReturn(responseB);

        List<UsuarioResponseDTO> resultado = listarUsuariosUseCase.executar();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(responseA));
        assertTrue(resultado.contains(responseB));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverUsuariosCadastrados() {
        when(usuarioRepository.listarUsuarios()).thenReturn(List.of());

        List<UsuarioResponseDTO> resultado = listarUsuariosUseCase.executar();

        assertTrue(resultado.isEmpty());
    }
}
