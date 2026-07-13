package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    private DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase;

    @BeforeEach
    void setUp() {
        deletarTipoUsuarioUseCase = new DeletarTipoUsuarioUseCase(tipoUsuarioRepository);
    }

    @Test
    void deveDeletarTipoUsuarioQuandoIdExistente() {
        UUID id = UUID.randomUUID();

        TipoUsuario tipoUsuario = new TipoUsuario("ADMIN");
        tipoUsuario.setId(id);

        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(id)).thenReturn(Optional.of(tipoUsuario));

        deletarTipoUsuarioUseCase.executar(id);

        verify(tipoUsuarioRepository).deletarTipoUsuario(id);
    }

    @Test
    void naoDevePermitirDeletarTipoUsuarioQuandoIdNaoExiste() {
        UUID id = UUID.randomUUID();

        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deletarTipoUsuarioUseCase.executar(id));

        verify(tipoUsuarioRepository, never()).deletarTipoUsuario(any());
    }
}
