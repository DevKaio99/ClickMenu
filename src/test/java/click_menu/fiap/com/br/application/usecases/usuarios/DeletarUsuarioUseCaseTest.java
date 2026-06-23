package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
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
public class DeletarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private DeletarUsuarioUseCase deletarUsuarioUseCase;

    @BeforeEach
    void setUp() {
        deletarUsuarioUseCase = new DeletarUsuarioUseCase(usuarioRepository);
    }

    @Test
    void deveDeletarUsuarioComIdExistente() {
        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.CLIENTE);

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));

        deletarUsuarioUseCase.executar(id);

        verify(usuarioRepository).deletarUsuario(id);

    }

    @Test
    void deveLancarExceptionQuandoIdNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deletarUsuarioUseCase.executar(id));

        verify(usuarioRepository, never()).deletarUsuario(any());
    }
}
