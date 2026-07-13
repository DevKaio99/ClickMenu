package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuscarUsuarioPorIdUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioMapper usuarioMapper;

    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @BeforeEach
    void setUp() {
        buscarUsuarioPorIdUseCase = new BuscarUsuarioPorIdUseCase(usuarioRepository, usuarioMapper);
    }

    @Test
    void deveBuscarUsuarioQuandoIdExistente() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");
        Usuario usuario = new Usuario("Teste", "teste@email.com", "123456", LocalDateTime.now(), tipoCliente);

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                id, "Teste", "teste@email.com", LocalDateTime.now(),
                new TipoUsuarioResponseDTO(tipoCliente.getId(), "CLIENTE"));

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.usuarioResponseDTO(usuario)).thenReturn(usuarioResponseDTO);

        UsuarioResponseDTO resultado = buscarUsuarioPorIdUseCase.executar(id);

        assertNotNull(resultado);
        assertEquals("Teste", resultado.nome());
    }

    @Test
    void naoDeveBuscarUsuarioQuandoIdInexistente() {
        UUID id = UUID.randomUUID();

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> buscarUsuarioPorIdUseCase.executar(id));
    }
}
